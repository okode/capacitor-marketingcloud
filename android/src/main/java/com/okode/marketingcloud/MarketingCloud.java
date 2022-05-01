package com.okode.marketingcloud;

import android.content.Context;
import android.util.Log;

import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import com.salesforce.marketingcloud.MarketingCloudConfig;
import com.salesforce.marketingcloud.notifications.NotificationCustomizationOptions;
import com.salesforce.marketingcloud.sfmcsdk.InitializationStatus;
import com.salesforce.marketingcloud.sfmcsdk.SFMCSdk;
import com.salesforce.marketingcloud.sfmcsdk.SFMCSdkModuleConfig;

import kotlin.Unit;

public class MarketingCloud {

    public String echo(String value) {
        Log.i("Echo", value);
        return value;
    }

    public void initialize(Context context, PluginCall call, String appId, String accessToken, String serverUrl) {

        SFMCSdkModuleConfig config = SFMCSdkModuleConfig.build(builder -> {
            int notificationIconResourceId = context.getResources().getIdentifier("ic_notification_icon", "drawable", context.getPackageName());
            builder.setPushModuleConfig(
                MarketingCloudConfig.builder()
                .setApplicationId(appId)
                .setAccessToken(accessToken)
                .setMarketingCloudServerUrl(serverUrl)
                .setNotificationCustomizationOptions(NotificationCustomizationOptions.create(notificationIconResourceId))
                .build(context)
            );
            return Unit.INSTANCE;
        });

        SFMCSdk.configure(context, config, status -> {
            call.resolve(new JSObject().put("success", status.getStatus() == InitializationStatus.SUCCESS));
            return Unit.INSTANCE;
        });
    }

}
