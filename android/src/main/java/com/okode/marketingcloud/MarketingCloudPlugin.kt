package com.okode.marketingcloud

import com.getcapacitor.JSObject
import com.getcapacitor.annotation.CapacitorPlugin
import com.getcapacitor.PluginMethod
import com.getcapacitor.PluginCall
import com.getcapacitor.Plugin
import com.salesforce.marketingcloud.sfmcsdk.InitializationStatus

@CapacitorPlugin(name = "MarketingCloud")
class MarketingCloudPlugin : Plugin() {

    private val implementation = MarketingCloud()

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
    fun enablePush(call: PluginCall) {
        implementation.enablePush()
    }

    @PluginMethod
    fun disablePush(call: PluginCall) {
        implementation.disablePush()
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
    fun handleNotification(call: PluginCall) {
        val notification = call.getObject("notification", JSObject())!!
        MarketingCloud.handleNotification(notification) {
            call.resolve(JSObject().put("value", it))
        }
    }

}