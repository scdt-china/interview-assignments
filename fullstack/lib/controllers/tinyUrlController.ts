import { Request, Response } from 'express';
import { insufficientParameters, mongoError, successResponse, failureResponse } from '../modules/common/service';
import { ITinyUrl } from '../modules/tinyurl/model';
import TinyUrlService from '../modules/tinyurl/service';
import e = require('express');

export class TinyUrlController {

    private tiny_url_service: TinyUrlService = new TinyUrlService();

    private getRandomInt(min, max) {
        min = Math.ceil(min);
        max = Math.floor(max);
        //The maximum is exclusive and the minimum is inclusive
        return Math.floor(Math.random() * (max - min) + min);
      }

    private reverseString(str) {
        // Step 1. Use the split() method to return a new array
        const splitString = str.split("");
     
        // Step 2. Use the reverse() method to reverse the new created array
        const reverseArray = splitString.reverse();
     
        // Step 3. Use the join() method to join all elements of the array into a string
        const joinArray = reverseArray.join("");
        
        //Step 4. Return the reversed string
        return joinArray;
    }

    private idToShortURL(id){
        // Map to store 62 possible characters
        const map = "abcdefghijklmnopqrstuvwxyzABCDEF" +
                    "GHIJKLMNOPQRSTUVWXYZ0123456789";
      
        let shorturl = "";
      
        // Convert given integer id to a base 62 number
        while (id >= 1){
            shorturl += map.charAt(id % 62);
            id = id / 62;
        }
      
        // Reverse shortURL to complete base conversion
        shorturl = this.reverseString(shorturl);
      
        return shorturl;
    }

    public create_tiny_url(req: Request, res: Response) {
        // this check whether all the filds were send through the request or not
        if (!req.body.url) {
            // error response if some fields are missing in request body
            insufficientParameters(res);
            return;
        }

        const id = new Date().getTime() * this.getRandomInt(1, 99) + new Date().getTime();

        const tiny_url_params: ITinyUrl = {
            original_url: req.body.url,
            tiny_url: this.idToShortURL(id),
            created_datetime: new Date(Date.now()),
            modification_notes: [{
                modified_on: new Date(Date.now()),
                modified_by: null,
                modification_note: 'New tiny url created'
            }]
        };
        
        this.tiny_url_service.createUser(tiny_url_params, (err: any, tiny_url_data: ITinyUrl) => {
            if (err) {
                mongoError(err, res);
            } else {
                successResponse(
                    'create tiny url successfull',
                    req.protocol + '://' + req.get('host') + req.originalUrl + "/" + tiny_url_data.tiny_url,
                    res
                );
            }
        });
    }

    public get_tiny_url(req: Request, res: Response) {
        if (!req.params.tiny_url) {
            insufficientParameters(res);
            return;
        }

        const tiny_url_filter = { tiny_url: req.params.tiny_url };
        this.tiny_url_service.filterUser(tiny_url_filter, (err: any, tiny_url_data: ITinyUrl) => {
            if (err) {
                mongoError(err, res);
            } else {
                successResponse(
                    'get original url ' + (tiny_url_data ? '' : 'not ') + 'successfull',
                    tiny_url_data && tiny_url_data.original_url,
                    res
                );
            }
        });
    }
}