package com.he.shorturl.utils;

public class UrlShorterGeneratorSimple implements UrlShorterGenerator<ShorterString> {

    private StringGenerator generator;
    private ShorterStorage<ShorterString> shorterStorage;

    public ShorterStorage<ShorterString> getShorterStorage() {
        return shorterStorage;
    }

    public void setShorterStorage(ShorterStorage<ShorterString> shorterStorage) {
        this.shorterStorage = shorterStorage;
    }

    public StringGenerator getGenerator() {
        return generator;
    }

    public void setGenerator(StringGenerator generator) {
        this.generator = generator;
    }

    public ShorterString generate(String url) {
        String shorter = generator.generate(url);
        while (shorterStorage.get(shorter) != null) {
            shorter = generator.generate(url);
        }
        ShorterString newShorter = new ShorterString(shorter);
        shorterStorage.save(url, newShorter);
        return newShorter;
    }
}
