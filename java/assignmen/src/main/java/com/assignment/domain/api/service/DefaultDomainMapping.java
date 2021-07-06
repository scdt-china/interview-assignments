package com.assignment.domain.api.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DefaultDomainMapping implements DomainMapping {
    private static final Map<String,String> CACHE_JVM = new ConcurrentHashMap<>(1000);

    @Override
    public void put(String shortDomain, String longDomain) {
        CACHE_JVM.put(shortDomain, longDomain);
    }

    @Override
    public Optional<String> takeLongDomain(String shortDomain) {
        return Optional.ofNullable(CACHE_JVM.get(shortDomain));
    }
}
