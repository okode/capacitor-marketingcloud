package com.okode.marketingcloud

import android.content.Context
import android.util.Log
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.salesforce.marketingcloud.messages.push.PushMessageManager
import com.salesforce.marketingcloud.sfmcsdk.SFMCSdk
import com.salesforce.marketingcloud.sfmcsdk.SFMCSdkModuleConfig
import org.json.JSONObject
import java.io.IOException

class MarketingCloud {

    companion object {

        private val MAPPER = ObjectMapper()

        fun initialize(context: Context,
                       appId: String,
                       accessToken: String,
                       serverUrl: String,
                       enableAnalytics: Boolean?,
                       listener: com.salesforce.marketingcloud.sfmcsdk.InitializationListener?) {
            SFMCSdk.configure(context, SFMCSdkModuleConfig.build {
                pushModuleConfig = MarketingCloudSdkConfig(
                        appId, accessToken, serverUrl, enableAnalytics).build(context)
            }, listener)
        }

        fun isMarketingCloudNotification(notification: JSONObject): Boolean {
            val notification = parseNotificationAsJSONObj(notification)
            return if (notification != null) isMarketingCloudNotification(notification) else false
        }

        fun isMarketingCloudNotification(notification: Map<String, String>): Boolean {
            return PushMessageManager.isMarketingCloudPush(notification)
        }

        fun showNotification(notification: JSONObject, listener: (success: Boolean) -> Unit) {
            val notification = parseNotificationAsJSONObj(notification)
            if (notification != null) {
                showNotification(notification, listener)
            } else {
                listener(false)
            }
        }

        fun showNotification(notification: Map<String, String>,
                               listener: (success: Boolean) -> Unit) {
            SFMCSdk.requestSdk {
                it.mp { mp -> listener(mp.pushMessageManager.handleMessage(notification)) }
            }
        }

        private fun parseNotificationAsJSONObj(notification: JSONObject): Map<String, String>? {
            return try {
                MAPPER.readValue(notification.toString(),
                        object : TypeReference<Map<String, String>>() {})
            } catch (e: IOException) {
                Log.e(javaClass.name, "Error parsing notification")
                null
            }
        }

    }

    fun setPushToken(token: String) {
        SFMCSdk.requestSdk {
            it.mp { mp -> mp.pushMessageManager.setPushToken(token) }
        }
    }

    fun setPushEnabled(enabled: Boolean) {
        SFMCSdk.requestSdk {
            it.mp { mp ->
                run {
                    if (enabled) { mp.pushMessageManager.enablePush() }
                    else { mp.pushMessageManager.disablePush() }
                }
            }
        }
    }

    fun setProfileId(id: String) {
        SFMCSdk.requestSdk { it.identity.setProfileId(id) }
    }

}