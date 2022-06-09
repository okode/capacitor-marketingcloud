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

    @objc public func isPushEnabled() -> Bool {
        return SFMCSdk.mp.pushEnabled()
    }

    @objc public func setPushEnabled(_ enabled: Bool) {
        SFMCSdk.mp.setPushEnabled(enabled)
    }

    @objc public func setProfileId(_ id: String) {
        SFMCSdk.identity.setProfileId(id)
    }

    @objc public func isMarketingCloudNotification(_ notification: [String:Any]) -> Bool {
        return notification["_sid"] as? String == "SFMC";
    }

    @objc public func notifyNotificationOpened(_ notification: [String:Any]) {
        SFMCSdk.mp.setNotificationUserInfo(notification)
    }

}
