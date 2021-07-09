package com.scdtchina.sdns.service;

import com.scdtchina.sdns.repository.LRUUrlPairsRepository;
import com.scdtchina.sdns.util.ShortUrlGenerator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShortUrlService {

    private final LRUUrlPairsRepository lruUrlPairsRepository;

    public String save(String normalUrl) {
        String repoShortUrl = lruUrlPairsRepository.findByNormalUrl(normalUrl);
        if (StringUtils.isNotEmpty(repoShortUrl)) {
            return repoShortUrl;
        }
        String shortUrl = ShortUrlGenerator.generateNext();
        shortUrl = lruUrlPairsRepository.addUrlPair(normalUrl, shortUrl);
        return shortUrl;
    }

    public String find(String shortUrl) {
        return lruUrlPairsRepository.findByShortUrl(shortUrl);
    }

}
