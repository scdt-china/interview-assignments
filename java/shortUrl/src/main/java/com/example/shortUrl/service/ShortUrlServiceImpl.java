package com.example.shortUrl.service;

import com.example.shortUrl.dao.ShortUrlMapper;
import com.example.shortUrl.domain.po.ShortUrl;
import com.example.shortUrl.utils.ShortUrlGenerator;
import java.util.Date;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author sting
 * @date 2021/5/20
 */
@Slf4j
@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    @Autowired
    ShortUrlMapper shortUrlMapper;

    @Value("${shortUrl.generator.baseUrl}")
    private String shortUrlBaseDomain;

    @Override
    public String generate(String url) {
        String shortCode = ShortUrlGenerator.shortCodeGenerate(url);
        if (StringUtils.isNotEmpty(shortCode)) {
            ShortUrl fullUrl = this.shortUrlMapper.selectFullUrlByShortCode(shortCode);
            if(StringUtils.isNotEmpty(shortCode) && fullUrl != null){
                if(url.equals(fullUrl.getFullUrl())){
                    return fullUrl.getBaseUrl() + fullUrl.getShortCode();
                }

            }
            if(!shortUrlBaseDomain.equals("/")){
                shortUrlBaseDomain += "/";
            }
            ShortUrl shortUrl = ShortUrl.builder().baseUrl(shortUrlBaseDomain).shortCode(shortCode)
                    .createTime(new Date()).fullUrl(url)
                    .expirationTime(DateUtils.addDays(new Date(), 7)).build();
            shortUrlMapper.insert(shortUrl);

            return shortUrlBaseDomain + shortCode;
        }
        return null;
    }

    @Override
    public String resolve(String shortUrl) {
        //https://tk.vip/edfg3s
        String shortCode = shortUrl.substring(shortUrl.lastIndexOf("/")+1);
        ShortUrl fullUrl = this.shortUrlMapper.selectFullUrlByShortCode(shortCode);
        return Optional.ofNullable(fullUrl).map(f -> f.getFullUrl())
                .orElse("");
    }
}
