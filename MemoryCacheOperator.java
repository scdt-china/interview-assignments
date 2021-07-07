package com.zdkj.handler.cache;

public interface MemoryCacheOperator<T> {

    void put(String key, T value, Long timeoutSeconds);

     T get(String key) ;

     boolean containsKey(String key);
}
