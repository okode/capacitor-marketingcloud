package com.okode.marketingcloud

import android.content.Context
import android.util.Log
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.firebase.messaging.RemoteMessage
import com.salesforce.marketingcloud.messages.push.PushMessageManager
import com.salesforce.marketingcloud.sfmcsdk.InitializationStatus
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
                       listener: (success: Boolean) -> Unit) {
            SFMCSdk.configure(context, SFMCSdkModuleConfig.build {
                pushModuleConfig = MarketingCloudSdkConfig(
                        appId, accessToken, serverUrl, enableAnalytics).build(context)
            }) {
                when (it.status) {
                    InitializationStatus.SUCCESS -> listener(true)
                    else -> listener(false)
                }
            }
        }

        fun setPushToken(token: String) {
            SFMCSdk.requestSdk {
                it.mp { mp -> mp.pushMessageManager.setPushToken(token) }
            }
        }

        fun isMarketingCloudNotification(notification: JSONObject): Boolean {
            val parsedNotification = parseNotificationAsJSONObj(notification)
            return if (parsedNotification != null) isMarketingCloudNotification(parsedNotification) else false
        }

        fun isMarketingCloudNotification(notification: Map<String, String>): Boolean {
            return PushMessageManager.isMarketingCloudPush(notification)
        }

        fun isMarketingCloudNotification(notification: RemoteMessage): Boolean {
            return PushMessageManager.isMarketingCloudPush(notification)
        }

        fun showNotification(notification: JSONObject, listener: (success: Boolean) -> Unit) {
            val parsedNotification = parseNotificationAsJSONObj(notification)
            if (parsedNotification != null) {
                showNotification(parsedNotification, listener)
            } else {
                listener(false)
            }
        }

        fun showNotification(notification: Map<String, String>,
                             listener: (success: Boolean) -> Unit?) {
            SFMCSdk.requestSdk {
                it.mp { mp -> listener(mp.pushMessageManager.handleMessage(notification)) }
            }
        }

        fun showNotification(notification: RemoteMessage,
                             listener: (success: Boolean) -> Unit?) {
            SFMCSdk.requestSdk {
                it.mp { mp -> listener(mp.pushMessageManager.handleMessage(notification)) }
            }
        }

        private fun parseNotificationAsJSONObj(notification: JSONObject): Map<String, String>? {
            return try {
                MAPPER.readValue(notification.toString(),
                        object : TypeReference<Map<String, String>>() {})
            } catch (e: IOException) {
                Log.e(MarketingCloud::class.java.name, "Error parsing notification")
                null
            }
        }

    }

    fun isPushEnabled(listener: (isEnabled: Boolean) -> Unit) {
        SFMCSdk.requestSdk {
            it.mp { mp -> listener(mp.pushMessageManager.isPushEnabled) }
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

    fun getProfileId(listener: (id: String?) -> Unit) {
        SFMCSdk.requestSdk { it.mp { mp ->
            listener(mp.moduleIdentity.profileId)
        }}
    }

    fun setProfileId(id: String) {
        SFMCSdk.requestSdk { it.identity.setProfileId(id) }
    }

    fun getAttributes(listener: (attrs: Map<String, String>) -> Unit) {
        SFMCSdk.requestSdk { it.mp { mp ->
            listener(mp.registrationManager.attributes)
        }}
    }

    fun setAttribute(key: String, value: String) {
        SFMCSdk.requestSdk { it.identity.setProfileAttribute(key, value) }
    }

    fun clearAttribute(key: String, listener: (success: Boolean) -> Unit) {
        SFMCSdk.requestSdk { it.mp { mp ->
            listener(mp.registrationManager.edit().clearAttribute(key).commit())
        }}
    }

    fun getTags(listener: (tags: Set<String>) -> Unit) {
        SFMCSdk.requestSdk { it.mp { mp ->
            listener(mp.registrationManager.tags)
        }}
    }

    fun addTag(tag: String, listener: (success: Boolean) -> Unit) {
        SFMCSdk.requestSdk { it.mp { mp ->
            listener(mp.registrationManager.edit().addTag(tag).commit())
        }}
    }

    fun removeTag(tag: String, listener: (success: Boolean) -> Unit) {
        SFMCSdk.requestSdk { it.mp { mp ->
            listener(mp.registrationManager.edit().removeTag(tag).commit())
        }}
    }

}