import { WebPlugin } from '@capacitor/core';

import type { MarketingCloudPlugin } from './definitions';

export class MarketingCloudWeb extends WebPlugin implements MarketingCloudPlugin {

  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

  async initialize(_: { appId: string, accessToken: string, serverUrl: string }): Promise<{ success: boolean }> {
    console.warn('MarketingCloudWeb initialize not implemented');
    return Promise.resolve<{ success: boolean }>({ success: false });
  }

}
