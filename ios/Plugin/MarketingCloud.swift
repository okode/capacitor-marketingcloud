import Foundation
import SFMCSDK
import MarketingCloudSDK

@objc public class MarketingCloud: NSObject {

    @objc static public func initialize(_ appId: String,
                                 _ accessToken: String,
                                 _ serverUrl: String,
                                 _ enableAnalytics: Bool,
                                 onCompletion: ((_ result: SFMCSDK.OperationResult) -> Void)? = nil) {

        let configuration = PushConfigBuilder(appId: appId)
            .setAccessToken(accessToken)
            .setMarketingCloudServerUrl(URL(string: serverUrl)!)
            .setAnalyticsEnabled(enableAnalytics)
            .build()

        SFMCSdk.initializeSdk(ConfigBuilder().setPush(
            config: configuration,
            onCompletion: onCompletion
        ).build())
    }

    @objc static public func setPushToken(_ token: Data) {
        SFMCSdk.mp.setDeviceToken(token)
    }

    @objc static public func extractNotificationMessage(_ notification: NSNotification) -> [String: Any]? {
        if let userInfo = notification.userInfo {
            if let notificationReq = userInfo["SFMCFoundationUNNotificationReceivedNotificationKeyUNNotificationRequest"] {
                return notificationReq as? [String:Any]
            }

            if let notificationUserInfo = userInfo["SFMCFoundationNotificationReceivedNotificationKeyUserInfo"] {
                return notificationUserInfo as? [String:Any]
            }
        }
        return nil;
    }

    @objc public func isPushEnabled() -> Bool {
        return SFMCSdk.mp.pushEnabled()
    }

    @objc public func setPushEnabled(_ enabled: Bool) {
        SFMCSdk.mp.setPushEnabled(enabled)
    }

    @objc public func getProfileId() -> String? {
        return SFMCSdk.mp.getIdentity()?.profileId
    }

    @objc public func setProfileId(_ id: String) {
        SFMCSdk.identity.setProfileId(id)
    }

    @objc public func getAttributes() -> [AnyHashable:Any]? {
        return SFMCSdk.mp.attributes() as? [String:Any]
    }

    @objc public func setAttribute(_ key: String, _ value: String) {
        SFMCSdk.identity.setProfileAttributes([ key: value ])
    }

    @objc public func clearAttribute(_ key: String) -> Bool {
        SFMCSdk.identity.clearProfileAttribute(key: key)
        return true
    }

    @objc public func getTags() -> Set<AnyHashable>? {
        return SFMCSdk.mp.tags()
    }

    @objc public func addTag(_ tag: String) -> Bool {
        return SFMCSdk.mp.addTag(tag)
    }

    @objc public func removeTag(_ tag: String) -> Bool {
        return SFMCSdk.mp.removeTag(tag)
    }

    @objc public func isMarketingCloudNotification(_ notification: [String:Any]) -> Bool {
        return notification["_sid"] as? String == "SFMC";
    }

    @objc public func notifyNotificationOpened(_ notification: [String:Any]) {
        SFMCSdk.mp.setNotificationUserInfo(notification)
    }

}
