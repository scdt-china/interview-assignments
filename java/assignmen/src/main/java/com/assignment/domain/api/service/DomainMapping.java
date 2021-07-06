package com.assignment.domain.api.service;

import java.util.Optional;

public interface DomainMapping {
    void put(String shortDomain, String longDomain);
    Optional<String> takeLongDomain(String shortDomain);
}
