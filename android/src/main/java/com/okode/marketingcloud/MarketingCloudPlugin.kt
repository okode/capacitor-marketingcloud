package com.okode.marketingcloud

import android.content.Intent
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin
import com.salesforce.marketingcloud.notifications.NotificationMessage
import org.json.JSONObject

@CapacitorPlugin(name = "MarketingCloud")
class MarketingCloudPlugin : Plugin() {

    private val implementation = MarketingCloud()

    override fun handleOnNewIntent(intent: Intent?) {
        super.handleOnNewIntent(intent)
        handleNotificationOpened(MarketingCloudSdkConfig.extractNotificationMessage(intent))
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
    fun getProfileId(call: PluginCall) {
        implementation.getProfileId() {
            call.resolve(JSObject().put("value", it))
        }
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
    fun getAttributes(call: PluginCall) {
        implementation.getAttributes { it
            call.resolve(JSObject().put("attributes", it))
        }
    }

    @PluginMethod
    fun setAttribute(call: PluginCall) {
        val key = call.getString("key")
        val value = call.getString("value")
        if (key == null || value == null) {
            call.reject("Invalid attribute key or value")
            return
        }
        implementation.setAttribute(key, value)
    }

    @PluginMethod
    fun clearAttribute(call: PluginCall) {
        val key = call.getString("key")
        if (key == null) {
            call.reject("Invalid attribute key")
            return
        }
        implementation.clearAttribute(key) {
            call.resolve(JSObject().put("value", it))
        }
    }

    @PluginMethod
    fun getTags(call: PluginCall) {
        implementation.getTags { it
            call.resolve(JSObject().put("tags", it))
        }
    }

    @PluginMethod
    fun addTag(call: PluginCall) {
        val tag = call.getString("value")
        if (tag == null) {
            call.reject("Invalid tag")
            return
        }
        implementation.addTag(tag) {
            call.resolve(JSObject().put("value", it))
        }
    }

    @PluginMethod
    fun removeTag(call: PluginCall) {
        val tag = call.getString("value")
        if (tag == null) {
            call.reject("Invalid tag")
            return
        }
        implementation.removeTag(tag) {
            call.resolve(JSObject().put("value", it))
        }
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
        val messageId = notification.payload?.get("_m")
        values.put("sfmcMessageId", messageId)
        notification.url?.let { values.put("url", it) }
        val notificationType = when(notification.type) {
            NotificationMessage.Type.OTHER -> "other"
            NotificationMessage.Type.CLOUD_PAGE -> "cloudPage"
            NotificationMessage.Type.OPEN_DIRECT -> "openDirect"
        }
        values.put("type", notificationType)
        val event = JSObject()
                .put("sfmcMessageId", messageId)
                .put("extras", values)
                .put("action", "tap")
        notifyListeners("notificationOpened", event, true)
    }

}