package com.okode.marketingcloud

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.FirebaseApp
import com.salesforce.marketingcloud.MarketingCloudConfig
import com.salesforce.marketingcloud.notifications.NotificationCustomizationOptions
import com.salesforce.marketingcloud.notifications.NotificationManager
import com.salesforce.marketingcloud.notifications.NotificationMessage
import java.util.*


data class MarketingCloudSdkConfig(val appId: String,
                                   val accessToken: String,
                                   val serverUrl: String,
                                   val enableAnalytics: Boolean?) {

    companion object {
        private const val DEFAULT_NOTIFICATION_CHANNEL_RES_NAME = "sfmcplugin_default_notification_channel_id"
        private const val NOTIFICATION_ICON_RES_NAME = "sfmcplugin_notification_icon"
        private const val NOTIFICATION_MSG_EXTRA = "sfmcplugin_notification_message_extra"

        fun extractNotificationMessage(intent: Intent?): NotificationMessage? {
            if (intent == null) { return null }
            return if (intent.hasExtra(NOTIFICATION_MSG_EXTRA)) {
                intent.getParcelableExtra(NOTIFICATION_MSG_EXTRA)
            } else NotificationManager.extractMessage(intent)
        }

        private fun getNotificationCustomizationOptions(): NotificationCustomizationOptions {
            return NotificationCustomizationOptions.create { context, notificationMessage ->
                val builder = NotificationManager.getDefaultNotificationBuilder(
                        context,
                        notificationMessage,
                        getNotificationChannelId(context, notificationMessage),
                        getNotificationIconResId(context)
                )
                builder.setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setDefaults(android.app.Notification.DEFAULT_VIBRATE)
                getNotificationContentIntent(context, notificationMessage)?.let {
                    builder.setContentIntent(it)
                }
                return@create builder
            }
        }

        private fun getNotificationChannelId(context: Context,
                                             notificationMessage: NotificationMessage): String {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                // Channel not supported in versions lower than Android 8 so we use the default impl
                return NotificationManager.createDefaultNotificationChannel(context)
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
                    as android.app.NotificationManager

            // Notification channel sent in the notification payload
            val notificationMessageChannel = notificationMessage.customKeys["channel"]?.let {
                notificationManager.getNotificationChannel(it)
            }
            if (notificationMessageChannel != null) { return notificationMessageChannel.id }

            // Default notification channel set up at project level
            val defaultNotificationChannel = notificationManager.getNotificationChannel(
                    getMarketingCloudDefaultChannelId(context))
            if (defaultNotificationChannel != null) { return defaultNotificationChannel.id }

            return NotificationManager.createDefaultNotificationChannel(context)
        }

        private fun getNotificationContentIntent(context: Context,
                                                 notificationMessage: NotificationMessage
        ): PendingIntent? {
            val launchPendingIntent = getLaunchPendingIntent(context, notificationMessage)
            return if (launchPendingIntent != null) {
                NotificationManager.redirectIntentForAnalytics(
                        context,
                        launchPendingIntent,
                        notificationMessage,
                        true
                )
            } else null
        }

        private fun getLaunchPendingIntent(context: Context,
                                           notificationMessage: NotificationMessage
        ): PendingIntent? {
            val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
            intent?.putExtra(NOTIFICATION_MSG_EXTRA, notificationMessage)
            intent?.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            return PendingIntent.getActivity(context, Random().nextInt(), intent,
                    PendingIntent.FLAG_CANCEL_CURRENT)
        }

        private fun getNotificationIconResId(context: Context): Int {
            val iconNameResId = context.resources.getIdentifier(
                    NOTIFICATION_ICON_RES_NAME, "string", context.packageName
            )
            val iconName = if (iconNameResId != 0) context.getString(iconNameResId) else ""
            return context.resources.getIdentifier(
                    iconName, "drawable", context.packageName
            )
        }

        private fun getMarketingCloudDefaultChannelId(context: Context): String? {
            val resId = context.resources.getIdentifier(
                    DEFAULT_NOTIFICATION_CHANNEL_RES_NAME, "string", context.packageName
            )
            return if (resId != 0) context.getString(resId) else null
        }

    }

    fun build(context: Context): MarketingCloudConfig {
        return MarketingCloudConfig.builder().apply {
            setApplicationId(appId)
            setAccessToken(accessToken)
            setMarketingCloudServerUrl(serverUrl)
            setAnalyticsEnabled(enableAnalytics ?: false)
            setNotificationCustomizationOptions(getNotificationCustomizationOptions())
            FirebaseApp.getInstance().options.gcmSenderId?.let {
                setSenderId(it)
            }
        }.build(context)
    }

}
