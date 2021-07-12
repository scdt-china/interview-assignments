package com.he.shorturl.service;

import com.he.shorturl.generator.StringGeneratorRandom;
import com.he.shorturl.storage.ShorterStorageMemory;
import com.he.shorturl.utils.ShorterGetter;
import com.he.shorturl.utils.UrlShorterGeneratorSimple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShortServiceImpl implements ShortService {

    @Autowired
    public ShorterStorageMemory storageMemory;

    public ShortServiceImpl(){
        this.storageMemory = new ShorterStorageMemory();
    }

    @Override
    public String create(String url) {
        UrlShorterGeneratorSimple simple = new UrlShorterGeneratorSimple();
        simple.setGenerator(new StringGeneratorRandom());
        simple.setShorterStorage(storageMemory);
        System.out.println(storageMemory);
        String generate = simple.generate(url).getShorter();
        System.out.println(generate);
        return generate;
    }

    @Override
    public String show(String shortCode) {
        String s = storageMemory.get(shortCode);
        return s;
    }
}
