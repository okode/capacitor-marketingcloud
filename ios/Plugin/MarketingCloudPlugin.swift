import Foundation
import Capacitor
import MarketingCloudSDK

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(MarketingCloudPlugin)
public class MarketingCloudPlugin: CAPPlugin {
    private let implementation = MarketingCloud()

    @objc public func initialize(_ call: CAPPluginCall) {
        let appId = call.getString("appId", "")
        let accessToken = call.getString("accessToken", "")
        let serverUrl = call.getString("serverUrl", "")
        let enableAnalytics = call.getBool("enableAnalytics", false)
        implementation.initialize(appId, accessToken, serverUrl, enableAnalytics) { result in
            if result == .success {
                call.resolve()
            } else if result == .error {
                call.reject("Module failed to initialize, check logs for more details")
            } else if result == .cancelled {
                call.reject("Module initialization was cancelled")
            } else if result == .timeout {
                call.reject("Module failed to initialize due to timeout, check logs for more details")
            }
        }
    }

    @objc public func setPushToken(_ call: CAPPluginCall) {
        guard let token = call.getString("token") else {
            return call.reject("Error setting push since its value is null")
        }
        implementation.setPushToken(token)
    }
    
    @objc public func isPushEnabled(_ call: CAPPluginCall) {
        let isEnabled = implementation.isPushEnabled()
        call.resolve([ "value": isEnabled ])
    }

    @objc public func setPushEnabled(_ call: CAPPluginCall) {
        guard let enabled = call.getBool("enabled") else {
            return call.reject("Error enabling push since the token is null")
        }
        implementation.setPushEnabled(enabled)
    }

    @objc public func setProfileId(_ call: CAPPluginCall) {
        guard let id = call.getString("value") else {
            return call.reject("Error setting profile id since its value is null")
        }
        implementation.setProfileId(id)
    }

    @objc public func isMarketingCloudNotification(_ call: CAPPluginCall) {
        let notification = call.getObject("notification", [:])
        let isSfmcPush = implementation.isMarketingCloudNotification(notification)
        call.resolve([ "value": isSfmcPush ])
    }

    @objc public func notifyNotificationOpened(_ call: CAPPluginCall) {
        let notification = call.getObject("notification", [:])
        implementation.notifyNotificationOpened(notification)
    }

}
