import { registerPlugin } from '@capacitor/core';

import type { MarketingCloudPlugin } from './definitions';

const MarketingCloud = registerPlugin<MarketingCloudPlugin>('MarketingCloud', {
  web: () => import('./web').then(m => new m.MarketingCloudWeb()),
});

export * from './definitions';
export { MarketingCloud };
