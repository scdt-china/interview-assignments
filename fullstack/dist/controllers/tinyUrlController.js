"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.TinyUrlController = void 0;
const service_1 = require("../modules/common/service");
const service_2 = require("../modules/tinyurl/service");
class TinyUrlController {
    constructor() {
        this.tiny_url_service = new service_2.default();
    }
    getRandomInt(min, max) {
        min = Math.ceil(min);
        max = Math.floor(max);
        //The maximum is exclusive and the minimum is inclusive
        return Math.floor(Math.random() * (max - min) + min);
    }
    reverseString(str) {
        // Step 1. Use the split() method to return a new array
        const splitString = str.split("");
        // Step 2. Use the reverse() method to reverse the new created array
        const reverseArray = splitString.reverse();
        // Step 3. Use the join() method to join all elements of the array into a string
        const joinArray = reverseArray.join("");
        //Step 4. Return the reversed string
        return joinArray;
    }
    idToShortURL(id) {
        // Map to store 62 possible characters
        const map = "abcdefghijklmnopqrstuvwxyzABCDEF" +
            "GHIJKLMNOPQRSTUVWXYZ0123456789";
        let shorturl = "";
        // Convert given integer id to a base 62 number
        while (id >= 1) {
            shorturl += map.charAt(id % 62);
            id = id / 62;
        }
        // Reverse shortURL to complete base conversion
        shorturl = this.reverseString(shorturl);
        return shorturl;
    }
    create_tiny_url(req, res) {
        // this check whether all the filds were send through the request or not
        if (!req.body.url) {
            // error response if some fields are missing in request body
            service_1.insufficientParameters(res);
            return;
        }
        const id = new Date().getTime() * this.getRandomInt(1, 99) + new Date().getTime();
        const tinyUrl = this.idToShortURL(id);
        const tiny_url_params = {
            original_url: req.body.url,
            tiny_url: this.idToShortURL(id),
            created_datetime: new Date(Date.now()),
            modification_notes: [{
                    modified_on: new Date(Date.now()),
                    modified_by: null,
                    modification_note: 'New tiny url created'
                }]
        };
        this.tiny_url_service.createUser(tiny_url_params, (err, tiny_url_data) => {
            if (err) {
                service_1.mongoError(err, res);
            }
            else {
                service_1.successResponse('create tiny url successfull', req.protocol + '://' + req.get('host') + req.originalUrl + "/" + tinyUrl, res);
            }
        });
    }
    get_tiny_url(req, res) {
        if (!req.params.tiny_url) {
            service_1.insufficientParameters(res);
            return;
        }
        const tiny_url_filter = { tiny_url: req.params.tiny_url };
        this.tiny_url_service.filterUser(tiny_url_filter, (err, tiny_url_data) => {
            if (err) {
                service_1.mongoError(err, res);
            }
            else {
                service_1.successResponse('get original url ' + (tiny_url_data ? '' : 'not') + 'successfull', tiny_url_data && tiny_url_data.original_url, res);
            }
        });
    }
}
exports.TinyUrlController = TinyUrlController;
