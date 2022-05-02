import { WebPlugin } from '@capacitor/core';
import type { MarketingCloudPlugin } from './definitions';

export class MarketingCloudWeb extends WebPlugin implements MarketingCloudPlugin {

  async echo(options: { value: string }) {
    console.log('ECHO', options);
    return Promise.resolve<{ value: string }>(options);
  }

  async initialize(_: { appId: string, accessToken: string, serverUrl: string }) {
    console.warn('MarketingCloudWeb initialize not implemented');
    return Promise.resolve<{ success: boolean }>({ success: false });
  }

}
