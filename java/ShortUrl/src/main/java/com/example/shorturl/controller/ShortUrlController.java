package com.example.shorturl.controller;

import com.example.shorturl.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zhu Lingfang
 * @version v1.0
 * @since 2021/06/05
 */
@Api(tags = "Url Converter")
@RestController
@RequestMapping("/shorturl")
public class ShortUrlController {
    @Autowired
    private ShortUrlService shortUrlService;

    @ApiOperation("Generate short url from origin url")
    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    public ResponseEntity<String> generate(@RequestParam("originUrl") String originUrl) {
        if (originUrl.length() == 0) return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        try {
            String shortUrl = shortUrlService.generateShortUrl(originUrl);
            return new ResponseEntity<>(shortUrl, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("Query origin url by short url")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ResponseEntity<String> query(@RequestParam("shortUrl") String shortUrl) {
        String originUrl = shortUrlService.queryOriginUrl(shortUrl);
        if (originUrl == null || originUrl.length() == 0) {
            return new ResponseEntity<>("Invalid Short Url", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(originUrl, HttpStatus.OK);
    }
}
