import type { PluginListenerHandle } from '@capacitor/core';

export interface MarketingCloudNotification {
  sfmcMessageId?: string;
  extras?: any;
  action: 'tap';
}

export interface MarketingCloudPlugin {
  initialize(options: {
    appId: string; accessToken: string; serverUrl: string; enableAnalytics?: boolean;
  }): Promise<void>;
  isPushEnabled(): Promise<{ value: boolean }>;
  setPushEnabled(opts: { enabled: boolean }): void;
  getProfileId(): Promise<{ value: string }>;
  setProfileId(opts: { value: string }): void;
  getAttributes(): Promise<{ attributes: string }>;
  setAttribute(opts: { key: string; value: string }): void;
  clearAttribute(opts: { key: string }): Promise<{ value: boolean }>;
  getTags(): Promise<{ tags: string[] }>;
  addTag(opts: { value: string }): Promise<{ value: boolean }>;
  removeTag(opts: { value: string }): Promise<{ value: boolean }>;
  isMarketingCloudNotification(opts: { notification: any }): Promise<{ value: boolean }>;
  /*
  * Only available on iOS
  */
  notifyNotificationOpened(opts: { notification: any }): void;
  /*
  * Only available on Android
  */
  showNotification(opts: { notification: any }): Promise<{ value: boolean }>;
  addListener(eventName: 'notificationOpened', listenerFunc: (notification: MarketingCloudNotification) => void): PluginListenerHandle;
}
