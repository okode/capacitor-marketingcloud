'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var core = require('@capacitor/core');

const MarketingCloud = core.registerPlugin('MarketingCloud', {
    web: () => Promise.resolve().then(function () { return web; }).then(m => new m.MarketingCloudWeb()),
});

class MarketingCloudWeb extends core.WebPlugin {
    async echo(options) {
        console.log('ECHO', options);
        return options;
    }
    async initialize(_) {
        console.warn('MarketingCloudWeb initialize not implemented');
        return Promise.resolve({ success: false });
    }
}

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    MarketingCloudWeb: MarketingCloudWeb
});

exports.MarketingCloud = MarketingCloud;
//# sourceMappingURL=plugin.cjs.js.map
