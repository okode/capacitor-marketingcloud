#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(MarketingCloudPlugin, "MarketingCloud",
           CAP_PLUGIN_METHOD(isPushEnabled, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(setPushEnabled, CAPPluginReturnNone);
           CAP_PLUGIN_METHOD(getProfileId, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(setProfileId, CAPPluginReturnNone);
           CAP_PLUGIN_METHOD(getAttributes, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(setAttribute, CAPPluginReturnNone);
           CAP_PLUGIN_METHOD(clearAttribute, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getTags, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(addTag, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(removeTag, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(isMarketingCloudNotification, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(notifyNotificationOpened, CAPPluginReturnNone);
)
