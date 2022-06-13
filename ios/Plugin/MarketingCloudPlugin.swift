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

    override public func load() {
        NotificationCenter.default.addObserver(self,
                                               selector: #selector(self.handleNotificationOpened(notification:)),
                                               name: NSNotification.Name.SFMCFoundationUNNotificationReceived,
                                               object: nil)
    }

    deinit {
        NotificationCenter.default.removeObserver(self)
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

    @objc public func getProfileId(_ call: CAPPluginCall) {
        call.resolve([ "value": implementation.getProfileId() ?? NSNull() ])
    }

    @objc public func setProfileId(_ call: CAPPluginCall) {
        guard let id = call.getString("value") else {
            return call.reject("Error setting profile id since its value is null")
        }
        implementation.setProfileId(id)
    }

    @objc public func getAttributes(_ call: CAPPluginCall) {
        call.resolve([ "attributes": implementation.getAttributes() ?? [:] ])
    }

    @objc public func setAttribute(_ call: CAPPluginCall) {
        guard let key = call.getString("key"), let value = call.getString("value") else {
            return call.reject("Invalid attribute key or value")
        }
        implementation.setAttribute(key, value)
    }

    @objc public func clearAttribute(_ call: CAPPluginCall) {
        guard let key = call.getString("key") else {
            return call.reject("Invalid attribute key")
        }
        call.resolve([ "value": implementation.clearAttribute(key) ])
    }

    @objc public func getTags(_ call: CAPPluginCall) {
        call.resolve([ "tags": implementation.getTags() ?? [] ])
    }

    @objc public func addTag(_ call: CAPPluginCall) {
        guard let tag = call.getString("value") else {
            return call.reject("Invalid tag")
        }
        call.resolve([ "value": implementation.addTag(tag) ])
    }

    @objc public func removeTag(_ call: CAPPluginCall) {
        guard let tag = call.getString("value") else {
            return call.reject("Invalid tag")
        }
        call.resolve([ "value": implementation.removeTag(tag) ])
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

    @objc public func handleNotificationOpened(notification: NSNotification) {
        guard var notificationMessage = MarketingCloud.extractNotificationMessage(notification) else {
            return;
        }

        let messageId = MarketingCloudNotification.getId(notificationMessage)
        if messageId != nil {
            notificationMessage["sfmcMessageId"] = messageId
        }

        func addUrl(_ url: String) { notificationMessage["url"] = url }

        func addType(_ type: String) {notificationMessage["type"] = type }

        if let openDirectUrl = MarketingCloudNotification.getOpenDirectUrl(notificationMessage) {
            addUrl(openDirectUrl)
            addType("openDirect")
        } else if let cloudPageUrl = MarketingCloudNotification.getCloudPageUrl(notificationMessage)  {
            addUrl(cloudPageUrl)
            addType("cloudPage")
        } else {
            addType("other")
        }

        notifyListeners("notificationOpened", data: [
            "sfmcMessageId": messageId ?? NSNull(),
            "extras": notificationMessage,
            "action": "tap"
        ])
    }

}
