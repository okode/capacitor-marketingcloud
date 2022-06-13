import Foundation
import SFMCSDK
import MarketingCloudSDK

@objc public class MarketingCloudNotification: NSObject {

    @objc static public func getId(_ notification: [String:Any]) -> String? {
        return notification["_m"] as? String
    }

    @objc static public func getOpenDirectUrl(_ notification: [String:Any]) -> String? {
        return notification["_od"] as? String
    }

    @objc static public func getCloudPageUrl(_ notification: [String:Any]) -> String? {
        return notification["_x"] as? String
    }

}
