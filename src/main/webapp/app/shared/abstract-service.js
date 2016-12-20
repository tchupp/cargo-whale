"use strict";
var http_1 = require("@angular/http");
var Rx_1 = require("rxjs/Rx");
var AbstractService = (function () {
    function AbstractService() {
    }
    AbstractService.handleError = function (error) {
        var errMsg;
        if (error instanceof http_1.Response) {
            var body = error.json() || '';
            var err = body.error || JSON.stringify(body);
            errMsg = error.status + " - " + (error.statusText || '') + " " + err;
        }
        else {
            errMsg = error.message ? error.message : error.toString();
        }
        console.error(errMsg);
        return Rx_1.Observable.throw(errMsg);
    };
    AbstractService.extractResponseBody = function (res) {
        return res.json() || {};
    };
    return AbstractService;
}());
exports.AbstractService = AbstractService;
