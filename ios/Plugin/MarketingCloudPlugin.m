#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(MarketingCloudPlugin, "MarketingCloud",
           CAP_PLUGIN_METHOD(initialize, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(enablePush, CAPPluginReturnNone);
           CAP_PLUGIN_METHOD(disablePush, CAPPluginReturnNone);
           CAP_PLUGIN_METHOD(setProfileId, CAPPluginReturnNone);
           CAP_PLUGIN_METHOD(isMarketingCloudNotification, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(notifyNotificationOpened, CAPPluginReturnNone);
)
