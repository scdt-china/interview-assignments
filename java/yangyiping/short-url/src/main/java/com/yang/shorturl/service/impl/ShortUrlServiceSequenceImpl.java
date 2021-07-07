package com.yang.shorturl.service.impl;


import com.yang.shorturl.dao.UrlRelationMapper;
import com.yang.shorturl.dao.entity.UrlRelationEntity;
import com.yang.shorturl.service.ShortUrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static com.yang.shorturl.Constant.*;

/**
 * @author yangyiping1
 */
@Slf4j
@Service
@ConfigurationProperties(prefix = "short.url.service")
public class ShortUrlServiceSequenceImpl implements ShortUrlService {

    private AtomicLong sequence;

    private String domain;


    /**
     * 数字字母集合
     */
    private Map<Long, Character> characters;
    /**
     * 数字字母索引
     */
    private Map<Character, Long> indexMap;

    public UrlRelationMapper urlRelationMapper;

    @Autowired
    public void setUrlRelationMapper(UrlRelationMapper urlRelationMapper) {
        this.urlRelationMapper = urlRelationMapper;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @PostConstruct
    private void init() {
        String intString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        //初始化序列,这里不做持久化处理,应用重启后从0开始
        sequence = new AtomicLong(0);
        characters = new ConcurrentHashMap<>(SIXTY_TWO);
        indexMap = new ConcurrentHashMap<>(SIXTY_TWO);
        for (int i = 0; i < SIXTY_TWO; i++) {
            Long index = (long) i;
            Character character = intString.charAt(i);
            characters.put(index, character);
            indexMap.put(character, index);
        }
    }

    /**
     * 转换一个序列短地址
     *
     * @param sequence
     * @return
     */
    private String getSequenceShortUrl(long sequence) {
        StringBuilder stringBuilders = new StringBuilder(SHORT_URL_LENGTH);
        long number = sequence;
        long remainder = 0;
        long count = 0;
        do {
            count++;
            remainder = number % SIXTY_TWO;
            stringBuilders.append(characters.get(remainder));
            number = number / SIXTY_TWO;
        } while (count < SHORT_URL_LENGTH);
        return stringBuilders.reverse().toString();
    }

    @Override
    public String getShortUrl(String url) {
        //简单实现,不做url合法性校验
        if (StringUtils.hasLength(url)) {
            long currentSequence = sequence.getAndAdd(LONG_ONE);
            String shortUrl = getSequenceShortUrl(currentSequence);
            //保存长地址和短地址的关系
            UrlRelationEntity urlRelationEntity
                    = UrlRelationEntity
                    .builder()
                    .id(currentSequence)
                    .shortUrl(shortUrl)
                    .url(url)
                    .build();
            if (urlRelationMapper.save(urlRelationEntity) > 0) {
                return domain + shortUrl;
            }
        }
        return null;
    }

    /**
     * 将短地址转换成id,以便高效查询长地址
     *
     * @param shortUrl
     * @return 短地址对应的id
     */
    private Long shortUrlToSequence(String shortUrl) {
        Long result = null;
        if (StringUtils.startsWithIgnoreCase(shortUrl, domain)) {
            long tempResult = 0;
            String reverseShortUrl = new StringBuilder(shortUrl.replace(domain, "")).reverse().toString();
            for (int i = 0; i < SHORT_URL_LENGTH; i++) {
                Character character = reverseShortUrl.charAt(i);
                Long index = indexMap.get(character);
                Double pow = Math.pow(SIXTY_TWO, i);
                tempResult += index * pow.longValue();
            }
            result = tempResult;
        }
        return result;
    }

    @Override
    public String getUrlFromShortUrl(String shortUrl) {
        Long id = shortUrlToSequence(shortUrl);
        if (Objects.nonNull(id)) {
            UrlRelationEntity urlRelationEntity = urlRelationMapper.findById(id);
            if (Objects.nonNull(urlRelationEntity)) {
                return urlRelationEntity.getUrl();
            }
        }
        return null;
    }
}
