package cn.dns.service.impl;

import cn.dns.service.inter.DnsService;
import cn.dns.utils.CheckArgsUtils;
import cn.dns.utils.ConversionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service("dnsService")
public class DnsServiceImpl implements DnsService {
    Logger logger = LoggerFactory.getLogger(DnsServiceImpl.class);
    private static ConcurrentHashMap<String,String> cache = new ConcurrentHashMap<>();
    AtomicInteger atomicInteger = new AtomicInteger(1);


    @Override
    public String getShortDns(String longDns){
        CheckArgsUtils.checkArgs(StringUtils.isEmpty(longDns),"longDns of args is  empty！");
        logger.debug("longDns:{}",longDns);
        String shortDns = ConversionUtils.convertToByLength(atomicInteger.incrementAndGet(),62,8);
        logger.debug("covert to shortDns:{}",shortDns);
        cache.put(shortDns,longDns);
        return shortDns;
    }

    @Override
    public String getLongDns(String shortDns) {
        CheckArgsUtils.checkArgs(StringUtils.isEmpty(shortDns),"shortDns of args is  empty!");
        return cache.get(shortDns);
    }




}
