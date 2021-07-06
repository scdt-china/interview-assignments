package com.assignment.domain.api.service;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultDomainService implements DomainService {
    private DomainGenerator domainGenerator;
    private DomainMapping domainMapping;

    public DefaultDomainService(DomainGenerator domainGenerator, DomainMapping domainMapping) {
        this.domainGenerator = domainGenerator;
        this.domainMapping = domainMapping;
    }

    @Override
    public String saveAndReply(String longDomainAddress) {
        String generatedShortDomain = domainGenerator.generate(longDomainAddress);
        domainMapping.put(generatedShortDomain, longDomainAddress);
        return generatedShortDomain;
    }

    @Override
    public Optional<String> read(String shortDomainAddress) {
        return domainMapping.takeLongDomain(shortDomainAddress);
    }
}
