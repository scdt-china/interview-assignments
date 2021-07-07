package com.sequoia.shorturl.service.impl;

import com.sequoia.shorturl.common.ResponseResult;
import com.sequoia.shorturl.service.ShortUrlService;
import com.sequoia.shorturl.util.ShortCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 *
 * 短域名服务业务实现类
 *
 * @Author xj
 *
 * @Date 2021/6/27
 *
 * @version v1.0.0
 *
 */
@Service
@Slf4j
public class ShortUrlServiceImpl implements ShortUrlService {

    // 本地缓存，防止内存溢出
    @Autowired
    Cache<String, String> shortCodeCache;  // 关系映射 根据shortCode查找oriUrl缓存
    @Autowired
    Cache<String, String> oriUrlCache;  //关系映射 根据原域名查找shortCode缓存

    @Value("${shorturl.prefix}")
    private String shortUrlPrefix;//转换的短域名网站前缀
    /**
     * 根据原地址返回短地址
     * @param originalUrl  要转换的原域名链接url
     * @return
     */
    @Override
    public ResponseResult createShortUrl(String originalUrl)
    {
        String shortUrl="";  //返回短域名变量
        String shortCode="";
        try{
             log.info("-----request originalUrl:{}",originalUrl);
             if(StringUtils.isEmpty(originalUrl)){
                 return new ResponseResult().responseError("参数错误,不能为空!");
             }
             shortCode=oriUrlCache.getIfPresent(originalUrl);
             if(StringUtils.isEmpty(shortCode)){
             //没命中缓存，重新生成
                 shortCode=ShortCodeUtil.getBatchShortCodeList().get(0);//使用批量发号器 取第一个袁术
                 shortCodeCache.put(shortCode,originalUrl);//放入短域名跟原域名映射
                 oriUrlCache.put(originalUrl,shortCode); //放入原域名跟短域名映射
                 if(shortCodeCache.getIfPresent(shortCode)!=null){ //已经存在
                      ShortCodeUtil.getBatchShortCodeList().remove(0);//删除用过的第一个元素
                }
             }

             shortUrl=shortUrlPrefix+shortCode; //域名前缀+短码 拼接为完整的短域名 做完返回结果
             log.info("----response shortUrl:{}",shortUrl);
             return new ResponseResult(0, "success",shortUrl);
          } catch(Exception e) {
              log.error("----ShortUrlServiceImpl.getShortUrl()，根据原地址返回短地址出现异常，异常信息为：\n", e);
              return new ResponseResult().responseError("处理失败");
          }
    }
    /**
     * 根据短地址返回原地址
     * @param shortUrl 请求参数 短域名变量名
     * @return
     */
    @Override
    public ResponseResult getOriginalUrl(String shortUrl)
    {
        try{
             log.info("----request shortUrl:{}",shortUrl);
             if(StringUtils.isEmpty(shortUrl)){
                return new ResponseResult().responseError("短域名url参数错误,不能为空!");
             }

            if(shortUrl.length()<shortUrlPrefix.length()+8){//校验长度
                return new ResponseResult().responseError("短域名url 不正确,正确的为:"+shortUrlPrefix+" 加8位短码");
            }

            if(!shortUrl.substring(0,shortUrlPrefix.length()).equals(shortUrlPrefix)){//短域名前缀是否正确
                return new ResponseResult().responseError("短域名url 前缀不正确,正确的为:"+shortUrlPrefix);
            }

            //截取域名的前缀得到转换的短码
            String shortCode=shortUrl.substring(shortUrlPrefix.length(),shortUrl.length());

            if(StringUtils.isEmpty(shortCode)){
                return new ResponseResult().responseError("生成的:"+shortCode+" 短码错误!");
            }
            log.info("----request shortCode:{}",shortCode);
            //从映射关系中得到原域名
            String originalUrl=shortCodeCache.getIfPresent(shortCode);
            log.info("----reponse originalUrl:{}",originalUrl);
            return new ResponseResult(0, "success", originalUrl);
        } catch(Exception e) {
           log.error("----ShortUrlServiceImpl.getOriginalUrl()，短地址返回原地址出现异常，异常信息为：\n", e);
           return new ResponseResult().responseError("处理失败");
        }
    }
}
