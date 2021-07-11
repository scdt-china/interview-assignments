package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShortController {

	@Autowired
	private ShortService shortService;

	@GetMapping("/w/{longName}")
	public String write(@PathVariable String longName) {
		return shortService.shortName(longName);
	}

	@GetMapping("/r/{shortName}")
	public String read(@PathVariable String shortName) {
		return shortService.longName(shortName);
	}
}