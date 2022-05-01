import { registerPlugin } from '@capacitor/core';
const MarketingCloud = registerPlugin('MarketingCloud', {
    web: () => import('./web').then(m => new m.MarketingCloudWeb()),
});
export * from './definitions';
export { MarketingCloud };
//# sourceMappingURL=index.js.map