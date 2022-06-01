export interface MarketingCloudPlugin {
  initialize(options: {
    appId: string; accessToken: string; serverUrl: string; enableAnalytics?: boolean;
  }): Promise<void>;
  enablePush(opts: { token: string }): void;
  disablePush(): void;
  setProfileId(opts: { value: string }): void;
  isMarketingCloudNotification(opts: { notification: any }): Promise<{ value: boolean }>;
  notifyNotificationOpened(opts: { notification: any }): void;
}
