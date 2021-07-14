package com.fh.shorturl.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shorturl.model.UrlInfo;
import com.fh.shorturl.mybatis.mapper.UrlInfoDao;
import com.fh.shorturl.service.ShortURLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Service
public class ShortURLServiceImpl implements ShortURLService {

    // 要使用生成 URL 的字符
    private static String[] urlchars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
            "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"
    };
    //签名盐值
    private static final String salt = "md5";

    @Autowired
    UrlInfoDao urlInfoDao;

    @Override
    public String addShortURL(@NotNull String longurl) {

        //根据url的sign去查重，如果不重，则新增
        String sign = SecureUtil.md5(salt + longurl);
        QueryWrapper<UrlInfo> cond = new QueryWrapper<UrlInfo>();
        cond.eq("sign", sign);
        int hascount = urlInfoDao.selectCount(cond);
        if (1 > hascount) {
            String shorturl = zip(sign);
            //new 对象并赋值
            UrlInfo urlInfo = new UrlInfo(longurl, shorturl, sign, new Date());
            int count = urlInfoDao.insert(urlInfo);
            if (1 == count) {
                return shorturl;
            }
        } else if (1 == hascount) {
            UrlInfo urlInfo = urlInfoDao.selectOne(cond);
            return urlInfo.getShortUrl();
        }
        return null;
    }

    /**
     * @param longurlsign
     * @return
     */
    public static String zip(String longurlsign) {
        String[] shorturl = new String[4];
        for (int i = 0; i < 4; i++) {
            // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
            String tmp = longurlsign.substring(i * 8, i * 8 + 8);
            // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用 long ，则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong(tmp, 16);
            StringBuilder outChars = new StringBuilder();
            for (int j = 0; j < 6; j++) {
                // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
                long index = 0x0000003D & lHexLong;
                // 把取得的字符相加
                outChars.append(urlchars[(int) index]);
                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> 5;
            }
            // 把字符串存入对应索引的输出数组
            shorturl[i] = outChars.toString();
        }
        return shorturl[0];
    }


    @Override
    @Cacheable(value = "short_url_key", key = "'url_'+ #shorturl")
    public String queryLongURL(String shorturl) {
        QueryWrapper<UrlInfo> cond = new QueryWrapper<UrlInfo>();
        cond.eq("short_url", shorturl);
        UrlInfo urlInfo = urlInfoDao.selectOne(cond);
        if (null != urlInfo) {
            return urlInfo.getLongUrl();
        } else {
            return null;
        }
    }
}
