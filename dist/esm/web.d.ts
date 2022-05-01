import { WebPlugin } from '@capacitor/core';
import type { MarketingCloudPlugin } from './definitions';
export declare class MarketingCloudWeb extends WebPlugin implements MarketingCloudPlugin {
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
    initialize(_: {
        appId: string;
        accessToken: string;
        serverUrl: string;
    }): Promise<{
        success: boolean;
    }>;
}
