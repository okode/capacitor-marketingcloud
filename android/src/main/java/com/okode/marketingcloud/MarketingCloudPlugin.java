package com.okode.marketingcloud;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "MarketingCloud")
public class MarketingCloudPlugin extends Plugin {

    private final MarketingCloud implementation = new MarketingCloud();

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }

    @PluginMethod
    public void initialize(PluginCall call) {
        final String appId = call.getString("appId");
        final String accessToken = call.getString("accessToken");
        final String serverUrl = call.getString("serverUrl");

        implementation.initialize(getBridge().getContext(), call, appId, accessToken, serverUrl);
    }

}
