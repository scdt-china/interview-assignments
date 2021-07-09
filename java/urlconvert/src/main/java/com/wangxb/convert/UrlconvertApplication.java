package com.wangxb.convert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@SpringBootApplication
public class UrlconvertApplication {

	public static void main(String[] args) throws URISyntaxException, MalformedURLException {
		SpringApplication.run(UrlconvertApplication.class, args);
	}

}
