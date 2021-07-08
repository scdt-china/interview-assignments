package com.cyz.shorturl.util;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName ShortUrlUtils
 * @Description 短连接生成工具
 * @Author CYZ
 * @Date 2021/07/04 0017 上午 13:11
 */
public class ShortUrlUtils {

    private static final String BASE = "LxF2vtuc3mW5AoBEIJ1wKfM4XOqPQR7ST8VbYZ6aH9dCDegNhijk0lnGUprsyz";
    //短域名url域名前缀
    private static String shortUrlPrefix = "http://a.cn/";
    //短域名存储的MAP
    private static Map<String, String> urlMap = new ConcurrentHashMap<String, String>();

    /**
     * 短域名生成
     * @param sequence  Long 型数字
     * @return String
     */
    public static String generateShortUrl(long sequence) {
        if(sequence<56800235583L){//最多转化成6位62进制
            String base62Str = base62Encode(sequence);
            //末尾添加两位随机字符，防止被遍历
            String base62RandomStr = base62Str+getRandomString(2);
            return shortUrlPrefix+base62RandomStr;
        }else{
            return null;
        }
    }

    /**
     * 短域名存储
     * @param num Long 型数字
     * @return String
     */
    public static void saveShortUrl(String shortUrl, String longUrl) {
        //检查map大小，定期清理数据，防止内存溢出
        if(urlMap.size()>10000){
            urlMap.clear();
        }
        urlMap.put(shortUrl,longUrl);

    }

    /**
     * 根据短域名获得长域名
     * @param shortUrl String
     * @return String
     */
    public static String getLongUrl(String shortUrl) {
        String longUrl = urlMap.get(shortUrl);
        return longUrl;
    }

    /**
     * 将数字转为62进制
     * @param num  Long 型数字
     * @return 62进制字符串
     */
    private static String base62Encode(long num) {
        StringBuilder sb = new StringBuilder();
        int targetBase = BASE.length();
        do {
            int i = (int) (num % targetBase);
            sb.append(BASE.charAt(i));
            num /= targetBase;
        } while (num > 0);

        return sb.reverse().toString();
    }

    /**
     * 随机生成两位字符串
     * @param length 产生字符串的长度
     * @return String
     */
    public static String getRandomString(int length){
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(BASE.charAt(number));
        }
        return sb.toString();
    }


}
