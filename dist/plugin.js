var capacitorMarketingCloud = (function (exports, core) {
    'use strict';

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

    Object.defineProperty(exports, '__esModule', { value: true });

    return exports;

})({}, capacitorExports);
//# sourceMappingURL=plugin.js.map
