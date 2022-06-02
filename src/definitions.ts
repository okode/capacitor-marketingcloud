import type { PluginListenerHandle } from '@capacitor/core';

export interface MarketingCloudNotification {
  timestamp: number;
  values?: any;
  action: 'tap';
}

export interface MarketingCloudPlugin {
  initialize(options: {
    appId: string; accessToken: string; serverUrl: string; enableAnalytics?: boolean;
  }): Promise<void>;
  enablePush(opts: { token: string }): void;
  disablePush(): void;
  setProfileId(opts: { value: string }): void;
  isMarketingCloudNotification(opts: { notification: any }): Promise<{ value: boolean }>;
  // Only on iOS
  notifyNotificationOpened(opts: { notification: any }): void;
  // Only on android
  handleNotification(opts: { notification: any }): Promise<{ value: boolean }>;
  // Only on android
  addListener(eventName: 'notificationOpened', listenerFunc: (notification: MarketingCloudNotification) => void): PluginListenerHandle;
}
