package com.thuangster.urlshortener.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thuangster.urlshortener.services.UrlshortenerService;

@RestController
public class UrlshortenerController {

	@Autowired
	UrlshortenerService urlshortenerService;

	@RequestMapping("/")
	public String index() {
		return "Welcome to Url shortener service!";
	}

	@PostMapping("/api/encode")
	public ResponseEntity<String> shortenUrl(@RequestBody String longUrl) {
		String shortUrl = urlshortenerService.encode(longUrl);
		return new ResponseEntity<>(shortUrl, HttpStatus.CREATED);
	}

	@GetMapping(value = "/api/{shortUrl}")
	public ResponseEntity<String> decode(@PathVariable String shortUrl) {
		if (shortUrl == null || shortUrl.length() != 8) {
			return new ResponseEntity<>("", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		String longUrl = urlshortenerService.decode(shortUrl);
		if (longUrl == null || longUrl.isEmpty()) {
			return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(longUrl, HttpStatus.OK);
		}
	}
}
