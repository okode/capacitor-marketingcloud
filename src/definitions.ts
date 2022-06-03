import type { PluginListenerHandle } from '@capacitor/core';

export interface MarketingCloudNotification {
  timestamp: number;
  message: string;
  sfmcType?: string;
  extras?: any;
  action: 'tap';
}

export interface MarketingCloudPlugin {
  initialize(options: {
    appId: string; accessToken: string; serverUrl: string; enableAnalytics?: boolean;
  }): Promise<void>;
  setPushToken(opts: { token: string }): void;
  setPushEnabled(opts: { enabled: boolean }): void;
  setProfileId(opts: { value: string }): void;
  isMarketingCloudNotification(opts: { notification: any }): Promise<{ value: boolean }>;
  /*
  * Only available on iOS
  */
  notifyNotificationOpened(opts: { notification: any }): void;
  /*
  * Only available on Android
  */
  showNotification(opts: { notification: any }): Promise<{ value: boolean }>;
  /*
  * Only available on Android
  */
  addListener(eventName: 'notificationOpened', listenerFunc: (notification: MarketingCloudNotification) => void): PluginListenerHandle;
}
