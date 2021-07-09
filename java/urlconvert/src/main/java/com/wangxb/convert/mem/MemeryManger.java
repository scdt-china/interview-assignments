package com.wangxb.convert.mem;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MemeryManger extends Thread {
    private CacheMap cacheMap;
    public MemeryManger( CacheMap cacheMap){
        this.cacheMap = cacheMap;
    }

    public void run() {

        while (true) {
            try {
                Thread.sleep(5*60*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long totalMem = Runtime.getRuntime().totalMemory() / 1024 / 1024;
            long freeMem = Runtime.getRuntime().freeMemory() / 1024 / 1024;
            double d = (double) freeMem / (double) totalMem;
            int n = (int) ( d * 100);
            if (n>=20){
                break;
            }
            int num = cacheMap.getSize()/10;
            cacheMap.clear(num);

        }

    }
}
