import Foundation

@objc public class MarketingCloud: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
