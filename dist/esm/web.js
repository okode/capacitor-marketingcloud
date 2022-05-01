import { WebPlugin } from '@capacitor/core';
export class MarketingCloudWeb extends WebPlugin {
    async echo(options) {
        console.log('ECHO', options);
        return options;
    }
    async initialize(_) {
        console.warn('MarketingCloudWeb initialize not implemented');
        return Promise.resolve({ success: false });
    }
}
//# sourceMappingURL=web.js.map