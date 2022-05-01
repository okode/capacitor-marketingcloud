export interface MarketingCloudPlugin {
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
    initialize(options: {
        appId: string;
        accessToken: string;
        serverUrl: string;
    }): Promise<{
        success: boolean;
    }>;
}
