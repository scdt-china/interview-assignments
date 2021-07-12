package com.he.shorturl.utils;

public class ShorterString implements ShorterGetter {
    private String shorter;

    public ShorterString() {
    }

    public ShorterString(String shorter) {
        setShorter(shorter);
    }

    public String getShorter() {
        return shorter;
    }

    public void setShorter(String shorter) {
        this.shorter = shorter;
    }


}
