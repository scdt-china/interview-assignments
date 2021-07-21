package org.dengzhiheng.shorturl.service.impl;

import com.github.yitter.idgen.YitIdHelper;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.dengzhiheng.shorturl.Cache.LruCacheV2;
import org.dengzhiheng.shorturl.Result.Result;
import org.dengzhiheng.shorturl.Result.ResultGenerator;
import org.dengzhiheng.shorturl.service.UrlConvertService;
import org.dengzhiheng.shorturl.utils.NumericConvertUtils;
import org.dengzhiheng.shorturl.utils.Validator;
import org.springframework.stereotype.Service;

/**
 * 短地址转换服务实现类
 * @Author: When6passBye
 * @Date: 2021-07-12 16:12
 **/
@Slf4j
@Service
public class UrlConverterServiceImpl implements UrlConvertService {

    public static final int Max_Len = 8;

    /**
     * 内存采用线程安全的LRU缓存，类似current HashMap的设计
     * @author : When6passBye
     * @date : 2021/7/19 上午9:25
     */
    private static final LruCacheV2<String, String> CACHE = new LruCacheV2<>(20000);

    @Override
    public Result<String> convertUrl(String url) {
        if(!Validator.checkUrl(url)){
            return ResultGenerator.genDefaultFailResult("长域名 URL 不合法，请检查输入的 URL ！");
        }
        log.info("convert start =====>[url]={}", url);
        String shortUrl;
        // 缓存命中（如果数据量大，考虑使用布隆过滤器）
        if(!Strings.isNullOrEmpty(CACHE.get(url))){
            shortUrl = CACHE.get(url);
            log.info("cache shot =====>[shortUrl]={}", shortUrl);
            return ResultGenerator.genSuccessResult(shortUrl);
        }
        // 生成一个新的短地址
        long nextId = YitIdHelper.nextId();
        // 转换为62进制（缩短长度）
        shortUrl = NumericConvertUtils.convertTo(nextId, 62);
        if(shortUrl.length()>Max_Len){
            return ResultGenerator.gentServerFailResult("生成的短域名URL长度过长!");
        }
        log.info("convert success =====>[shortUrl]={}", shortUrl);
        // 放入缓存（如果数据量比较大或者高并发可以将此步骤异步化）
        CACHE.put(shortUrl,url);
        CACHE.put(url,shortUrl);
        return ResultGenerator.genSuccessResult(shortUrl);
    }

    @Override
    public Result<String> revertUrl(String shortUrl) {
        if (!Validator.checkShortUrl(shortUrl)) {
            return ResultGenerator.genDefaultFailResult("短域名 URL 不合法，请检查输入的 URL ！");
        }
        log.info("revert start =====>[shortUrl]={}", shortUrl);
        String shortcut = shortUrl.substring(shortUrl.lastIndexOf("/") + 1);
        //如果没有找到对应的长地址，就返回null
        String url = CACHE.get(shortcut);
        if(null == url){
            return ResultGenerator.genDefaultFailResult(null);
        }
        log.info("revert success =====>[original Url]={}", url);
        return ResultGenerator.genSuccessResult(url);
    }
}
