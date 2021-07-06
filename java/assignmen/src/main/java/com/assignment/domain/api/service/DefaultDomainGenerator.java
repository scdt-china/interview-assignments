package com.assignment.domain.api.service;

import com.google.common.hash.*;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;

@Service
public class DefaultDomainGenerator implements DomainGenerator {
    private final DomainMapping domainMapping;
    private static final BloomFilter<Integer> BLOOM_FILTER = BloomFilter.create(Funnels.integerFunnel(),
            1000000, 0.001);

    final String baseShortDomain;

    public DefaultDomainGenerator(DomainMapping domainMapping, String baseShortDomain) {
        this.domainMapping = domainMapping;
        this.baseShortDomain = baseShortDomain;
    }

    @Override
    public String generate(String longDomainAddress) {
        HashFunction hashFunction = Hashing.murmur3_128();
        HashCode hashCode = hashFunction.hashString(longDomainAddress, StandardCharsets.UTF_8);
        int longDomainHashCode = Math.abs(hashCode.asInt());
        boolean domainExists = BLOOM_FILTER.mightContain(longDomainHashCode);

        String shortDomain = baseShortDomain + encode(longDomainHashCode);
        if (!domainExists) {
            BLOOM_FILTER.put(longDomainHashCode);
            domainMapping.put(shortDomain, longDomainAddress);
        }
        return shortDomain;
    }

    private String encode(long longDomainHashCode) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int scale = 52;
        StringJoiner joiner = new StringJoiner("");
        int remainder;
        while (longDomainHashCode > scale - 1) {
            remainder = Long.valueOf(longDomainHashCode % scale).intValue();
            joiner.add(String.valueOf(chars.charAt(remainder)));
            longDomainHashCode = longDomainHashCode / scale;
        }
        joiner.add(String.valueOf(chars.charAt(Long.valueOf(longDomainHashCode).intValue())));
        return joiner.toString();
    }
}
