package com.example.shorturlproj.service;


import java.util.concurrent.ExecutionException;

public interface ShortUrlTransformService{
    public String getShortUrlFromLongUrl(String longUrl) throws ExecutionException;
    public String getLongUrlFromShortUrl(String shortUrl) throws ExecutionException;
}