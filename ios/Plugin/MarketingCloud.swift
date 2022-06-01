import Foundation
import SFMCSDK
import MarketingCloudSDK

@objc public class MarketingCloud: NSObject {

    @objc public func initialize(_ appId: String,
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

    @objc public func enablePush(_ token: String) {
        guard let tokenData = token.data(using: .utf8) else {
            return
        }
        SFMCSdk.mp.setDeviceToken(tokenData)
        SFMCSdk.mp.setPushEnabled(true)
    }

    @objc public func disablePush() {
        SFMCSdk.mp.setPushEnabled(false)
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
