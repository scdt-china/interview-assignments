package com.example.demo.dto;

public class Repository {

	private String shortUrl;
	private String normalUrl;
	private String expires;

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public String getNormalUrl() {
		return normalUrl;
	}

	public void setNormalUrl(String normalUrl) {
		this.normalUrl = normalUrl;
	}

	public String getExpires() {
		return expires;
	}

	public void setExpires(String expires) {
		this.expires = expires;
	}
}
