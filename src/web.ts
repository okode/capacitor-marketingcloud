import { WebPlugin } from '@capacitor/core';

import type { MarketingCloudPlugin } from './definitions';

export class MarketingCloudWeb extends WebPlugin implements MarketingCloudPlugin {

  initialize(_opts: { appId: string; accessToken: string; serverUrl: string; enableAnalytics?: boolean; }): Promise<void> {
    return Promise.reject('Method not implemented.');
  }

  setPushToken(_opts: { token: string; }): void {
    throw new Error('Method not implemented.');
  }

  isPushEnabled(): Promise<{ value: boolean; }> {
    return Promise.reject('Method not implemented.');
  }

  setPushEnabled(_opts: { enabled: boolean }): void {
    throw new Error('Method not implemented.');
  }

  setProfileId(_opts: { value: string; }): void {
    throw new Error('Method not implemented.');
  }

  isMarketingCloudNotification(_opts: { notification: any; }): Promise<{ value: boolean; }> {
    return Promise.reject('Method not implemented.');
  }

  notifyNotificationOpened(_opts: { notification: any; }): void {
    throw new Error('Method not implemented.');
  }

  showNotification(_opts: { notification: any; }): Promise<{ value: boolean; }> {
    return Promise.reject('Method not implemented.');
  }

}
