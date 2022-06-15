import { WebPlugin } from '@capacitor/core';

import type { MarketingCloudPlugin } from './definitions';

export class MarketingCloudWeb extends WebPlugin implements MarketingCloudPlugin {

  isPushEnabled(): Promise<{ value: boolean; }> {
    return Promise.reject('Method not implemented.');
  }

  setPushEnabled(_opts: { enabled: boolean }): void {
    throw new Error('Method not implemented.');
  }

  getProfileId(): Promise<{ value: string; }> {
    return Promise.reject('Method not implemented.');
  }

  setProfileId(_opts: { value: string; }): void {
    throw new Error('Method not implemented.');
  }

  getAttributes(): Promise<{ attributes: string; }> {
    return Promise.reject('Method not implemented.');
  }

  setAttribute(_opts: { key: string; value: string; }): void {
    throw new Error('Method not implemented.');
  }

  clearAttribute(_opts: { key: string; }): Promise<{ value: boolean; }> {
    return Promise.reject('Method not implemented.');
  }

  getTags(): Promise<{ tags: string[]; }> {
    return Promise.reject('Method not implemented.');
  }

  addTag(_opts: { value: string; }): Promise<{ value: boolean; }> {
    return Promise.reject('Method not implemented.');
  }

  removeTag(_opts: { value: string; }): Promise<{ value: boolean; }> {
    return Promise.reject('Method not implemented.');
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
