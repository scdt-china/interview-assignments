package com.scdtchina.sdns.util;

import lombok.Getter;
import lombok.Setter;

public class CacheNode<T> {
    @Getter
    @Setter
    private CacheNode<T> prev;
    @Getter
    @Setter
    private CacheNode<T> next;
    @Getter
    @Setter
    private T val;

    public CacheNode(T val) {
        this.val = val;
    }

}
