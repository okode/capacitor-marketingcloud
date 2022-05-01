import { WebPlugin } from '@capacitor/core';

import type { MarketingCloudPlugin } from './definitions';

export class MarketingCloudWeb
  extends WebPlugin
  implements MarketingCloudPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
