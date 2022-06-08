package com.okode.marketingcloud

import android.content.Intent
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin
import com.salesforce.marketingcloud.notifications.NotificationMessage
import com.salesforce.marketingcloud.sfmcsdk.InitializationStatus
import org.json.JSONObject

@CapacitorPlugin(name = "MarketingCloud")
class MarketingCloudPlugin : Plugin() {

    private val implementation = MarketingCloud()

    override fun handleOnNewIntent(intent: Intent?) {
        super.handleOnNewIntent(intent)
        handleNotificationOpened(MarketingCloudSdkConfig.extractNotificationMessage(intent))
    }

    @PluginMethod
    fun initialize(call: PluginCall) {
        val appId = call.getString("appId", "")!!
        val accessToken = call.getString("accessToken", "")!!
        val serverUrl = call.getString("serverUrl", "")!!
        val enableAnalytics = call.getBoolean("enableAnalytics", false)!!
        MarketingCloud.initialize(bridge.context, appId, accessToken, serverUrl, enableAnalytics) {
            if (it.status == InitializationStatus.SUCCESS) {
                call.resolve()
            } else {
                call.reject("Error initializing Marketing cloud")
            }
        }
    }

    @PluginMethod
    fun setPushToken(call: PluginCall) {
        val token = call.getString("token", "")!!
        implementation.setPushToken(token)
    }

    @PluginMethod
    fun isPushEnabled(call: PluginCall) {
        implementation.isPushEnabled {
            call.resolve(JSObject().put("value", it))
        }
    }

    @PluginMethod
    fun setPushEnabled(call: PluginCall) {
        val enabled = call.getBoolean("enabled", true)!!
        implementation.setPushEnabled(enabled)
    }

    @PluginMethod
    fun setProfileId(call: PluginCall) {
        val profileId = call.getString("value")
        if (profileId == null) {
            call.reject("Error setting profile id since its value is null")
            return
        }
        implementation.setProfileId(profileId)
    }

    @PluginMethod
    fun isMarketingCloudNotification(call: PluginCall) {
        val notification = call.getObject("notification", JSObject())!!
        val result = MarketingCloud.isMarketingCloudNotification(notification)
        call.resolve(JSObject().put("value", result))
    }

    @PluginMethod
    fun showNotification(call: PluginCall) {
        val notification = call.getObject("notification", JSObject())!!
        MarketingCloud.showNotification(notification) {
            call.resolve(JSObject().put("value", it))
        }
    }

    private fun handleNotificationOpened(notification: NotificationMessage?) {
        if (notification == null) { return }
        val values = JSObject.fromJSONObject(JSONObject(notification.payload))
        notification.url?.let { values.put("url", it) }
        val notificationType = when(notification.type) {
            NotificationMessage.Type.OTHER -> "other"
            NotificationMessage.Type.CLOUD_PAGE -> "cloudPage"
            NotificationMessage.Type.OPEN_DIRECT -> "openDirect"
        }
        values.put("type", notificationType)
        val event = JSObject()
                .put("timestamp", System.currentTimeMillis())
                .put("message", notification.alert)
                .put("sfmcType", notification.payload?.get("_m"))
                .put("extras", values)
                .put("action", "tap")
        notifyListeners("notificationOpened", event, true)
    }

}