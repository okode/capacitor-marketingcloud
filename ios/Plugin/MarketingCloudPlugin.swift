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

    @objc func echo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }
    
    @objc public func initialize(_ call: CAPPluginCall) {
        let appId = call.getString("appId") ?? ""
        let accessToken = call.getString("accessToken") ?? ""
        let serverUrl = call.getString("serverUrl") ?? ""
        implementation.initialize(appId, accessToken, serverUrl) { result in
            call.resolve([ "success": result == OperationResult.success ])
        }
    }
    
}
