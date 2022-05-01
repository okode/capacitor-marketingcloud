import Foundation
import SFMCSDK
import MarketingCloudSDK

@objc public class MarketingCloud: NSObject {
    
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
    
    @objc public func initialize(_ appId: String,
                                 _ accessToken: String,
                                 _ serverUrl: String,
                                 onCompletion: ((_ result: SFMCSDK.OperationResult) -> Void)? = nil) {

        let configuration = PushConfigBuilder(appId: appId)
            .setAccessToken(accessToken)
            .setMarketingCloudServerUrl(URL(string: serverUrl)!)
            .build()

        SFMCSdk.initializeSdk(ConfigBuilder().setPush(
            config: configuration,
            onCompletion: onCompletion
        ).build())

    }
    
}
