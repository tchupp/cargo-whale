'use strict';

var gulp = require('gulp'),
    url = require('url'),
    browserSync = require('browser-sync'),
    proxy = require('proxy-middleware');

var config = require('./config'),
    util = require('./utils');

module.exports = function () {
    var baseUri = config.uri + config.apiPort;

    var proxyRoutes = [
        '/api'
    ];

    var requireTrailingSlash = proxyRoutes.filter(function (r) {
        return util.endsWith(r, '/');
    }).map(function (r) {
        // Strip trailing slash so we can use the route to match requests
        // with non trailing slash
        return r.substr(0, r.length - 1);
    });

    var proxies = [
        function (req, res, next) {
            requireTrailingSlash.forEach(function (route) {
                if (url.parse(req.url).path === route) {
                    res.statusCode = 301;
                    res.setHeader('Location', route + '/');
                    res.end();
                }
            });

            next();
        }
    ]
        .concat(
            proxyRoutes.map(function (r) {
                var options = url.parse(baseUri + r);
                options.route = r;
                options.preserveHost = true;
                return proxy(options);
            }));

    browserSync({
        open: true,
        port: config.port,
        server: {
            baseDir: config.dist,
            middleware: proxies
        }
    });

    gulp.start('watch');
};