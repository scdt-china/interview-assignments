package com.example.shortUrl.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * @author shiqingshan
 * @desc
 * @Param
 * @Date 2021/5/20 上午9:38
 * @return
 **/
@Slf4j
public class ShortUrlGenerator {

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private static final String regex = "(ht|f)tp(s?)\\:\\/\\/[0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*(:(0-9)*)*(\\/?)([a-zA-Z0-9\\-\\.\\?\\,\\'\\/\\\\&%\\+\\$#_=]*)?";

    /**
     * @return java.lang.String
     * @desc 短链接生成
     * @author shiqingshan
     * @Param [url] 正常url
     * @Date 2021/5/20 上午10:28
     **/
    public static String shortCodeGenerate(String url) {

        // 要使用生成 URL 的字符
        String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"
        };
        String key = "key";
        if (StringUtils.isEmpty(key)) {
            key = new StringBuffer().append(RANDOM.nextInt(chars.length - 1))
                    .append(RANDOM.nextInt(chars.length - 1))
                    .append(RANDOM.nextInt(chars.length - 1)).toString();
        }
        // 对传入网址进行 MD5 加密
        String sMD5EncryptResult = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update((key + url).getBytes());
            byte[] digest = md.digest();
            sMD5EncryptResult = DatatypeConverter.printHexBinary(digest).toUpperCase();
            log.debug("{} MD5 后结果 {}", url, sMD5EncryptResult);
        } catch (NoSuchAlgorithmException e) {
            log.error("对urlMD5加密异常：", e);
        }

        //目标shortUrl
        String resUrl = "";

        for (int i = 0; i < 8; i++) {
            String sTempSubString = sMD5EncryptResult.substring(i * 4, i * 4 + 4);
            // 这里需要使用 long 型来转换，因为 Integer.parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用 long ，则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
            String outChars = "";
            long index = 0x0000003D & lHexLong;
            outChars += chars[(int) index];
            resUrl += outChars;
        }
        return resUrl;
    }


    /**
     * @desc 验证url
     * @author shiqingshan
     * @Param [urls]
     * @Date 2021/5/20 上午11:45
     * @return boolean
    **/
    public static boolean isUrl(String urls) {
        if (StringUtils.isBlank(urls)) {
            return false;
        }

        Pattern pat = Pattern.compile(regex);
        Matcher mat = pat.matcher(urls.trim());
        return mat.matches();
    }
}