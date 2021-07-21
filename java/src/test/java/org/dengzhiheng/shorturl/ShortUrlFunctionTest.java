package org.dengzhiheng.shorturl;

import org.dengzhiheng.shorturl.Cache.LruCacheV1;
import org.dengzhiheng.shorturl.Cache.LruCacheV2;
import org.dengzhiheng.shorturl.Result.Result;
import org.dengzhiheng.shorturl.configuration.ServerInitConfiguration;
import org.dengzhiheng.shorturl.controller.IndexController;
import org.dengzhiheng.shorturl.controller.MainController;
import org.dengzhiheng.shorturl.utils.NumericConvertUtils;
import org.dengzhiheng.shorturl.utils.RamMonitor;
import org.dengzhiheng.shorturl.utils.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.ModelAndView;

import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: When6passBye
 * @Date: 2021-07-19 13:50
 **/
@SpringBootTest
public class ShortUrlFunctionTest {
    @Autowired
    private MainController mainController;
    @Autowired
    private IndexController indexController;
    @Autowired
    private ServerInitConfiguration serverInitConfiguration;

    private static final int MAX_LEN = 8;

    @Test
    void contextLoads() {
        ShortUrlApplication.main(new String[]{"test"});
    }

    /**
     * 长域名转短域名正常转换：
     * 1.返回值不为空
     * 2.返回值长度不超过8位
     * 3.返回值只能是数字和大小写字母的组合，不能有其他字符
     * @author : When6passBye
     * @date : 2021/7/19 下午2:18
     * @return : void
     */
    @Test
    public void Long2ShortTest(){
        String url = "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=%40ApiResponses&fenlei=256&oq=swagger&rsv_pq=91dc3c390000c5e4&rsv_t=b775VIRsl4YDXcAhROGmRORwLvu6OKZi3KNHMNxMdaVK5FTDrRTvgBuTxjQ&rqlang=cn&rsv_enter=1&rsv_dl=tb&rsv_sug3=2&rsv_sug1=3&rsv_sug7=100&rsv_sug2=0&rsv_btype=t&inputT=1808&rsv_sug4=2417";
        String shortUrl = mainController.convertUrl(url).getData();
        assertNotNull(shortUrl);
        assertTrue(shortUrl.length()<=MAX_LEN);
        String regex = "^[a-z0-9A-Z]+$";
        assertTrue(shortUrl.matches(regex));
    }

    /**
     * 长域名转短域名正常转换：
     * 缓存命中：以同样的长域名调用接口，会命中缓存，并返回同样的短域名
     * @author : When6passBye
     * @date : 2021/7/19 下午2:54
     * @return : void
     */
    @Test
    public void Long2ShortCacheTest(){
        String url = "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=%40ApiResponses&fenlei=256&oq=swagger&rsv_pq=91dc3c390000c5e4&rsv_t=b775VIRsl4YDXcAhROGmRORwLvu6OKZi3KNHMNxMdaVK5FTDrRTvgBuTxjQ&rqlang=cn&rsv_enter=1&rsv_dl=tb&rsv_sug3=2&rsv_sug1=3&rsv_sug7=100&rsv_sug2=0&rsv_btype=t&inputT=1808&rsv_sug4=2417";
        String shortUrl = mainController.convertUrl(url).getData();
        assertNotNull(shortUrl);
        assertTrue(shortUrl.length()<=MAX_LEN);
        String regex = "^[a-z0-9A-Z]+$";
        assertTrue(shortUrl.matches(regex));
        String shortUrl2 = mainController.convertUrl(url).getData();
        assertEquals(shortUrl, shortUrl2);
    }

    /**
     * 长域名转短域名非正常转换：
     * URL格式不正常，需要识别并返回400code
     * @author : When6passBye
     * @date : 2021/7/19 下午2:40
     * @return : void
     */
    @Test
    public void Long2ShortWrongTest(){
        String url = "hs://老师都看过vdsoig";
        Result<String> result = mainController.convertUrl(url);
        assertNotNull(result);
        assertEquals(400, result.getCode());
    }



    /**
     * 短域名还原长域名正常转换：
     * 存在且没有被内存淘汰的情况下：
     * 1.返回值不为空
     * 2.返回的长域名与输入的长域名一致
     * @author : When6passBye
     * @date : 2021/7/19 下午2:18
     * @return : void
     */
    @Test
    public void Short2LongTest(){
        String url = "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=%40ApiResponses&fenlei=256&oq=swagger&rsv_pq=91dc3c390000c5e4&rsv_t=b775VIRsl4YDXcAhROGmRORwLvu6OKZi3KNHMNxMdaVK5FTDrRTvgBuTxjQ&rqlang=cn&rsv_enter=1&rsv_dl=tb&rsv_sug3=2&rsv_sug1=3&rsv_sug7=100&rsv_sug2=0&rsv_btype=t&inputT=1808&rsv_sug4=2417";
        String shortUrl = mainController.convertUrl(url).getData();
        assertNotNull(shortUrl);
        assertTrue(shortUrl.length()<=MAX_LEN);
        String regex = "^[a-z0-9A-Z]+$";
        assertTrue(shortUrl.matches(regex));
        //=========
        Result<String> result = mainController.revertUrl(shortUrl);
        String longUrl = result.getData();
        assertNotNull(longUrl);
        assertEquals(longUrl, url);
    }

    /**
     * 短域名转长域名非正常转换：
     * 短域名不存在
     * 1.非法短域名 返回code 为400
     * 2.不存在的合法域名 返回值 data为null
     * @author : When6passBye
     * @date : 2021/7/19 下午3:37
     * @return : void
     */
    @Test
    public void Short2LongCacheTest(){
        String shortUrl =  "我是非法短域名哈哈哈哈哈";
        Result<String> result = mainController.revertUrl(shortUrl);
        int code = result.getCode();
        assertEquals(400, code);
        //==========
        shortUrl = "PO9aXAW1";
        result = mainController.revertUrl(shortUrl);
        assertNull(result.getData());
        //=========
        result = mainController.revertUrl(null);
        assertNull(result.getData());
    }

    /**
     * 测试生成ID如果是负数的情况
     * @author : When6passBye
     * @date : 2021/7/19 下午4:35
     * @return : void
     */
    @Test
    public void NumericConvertTest(){
        String shortUrl = NumericConvertUtils.convertTo(-123456789, 62);
        String regex = "^[a-z0-9A-Z]+$";
        assertTrue(shortUrl.matches(regex));
    }

    /**
     * 报错后，获取返回的消息
     * @author : When6passBye
     * @date : 2021/7/19 下午4:45
     * @return : void
     */
    @Test
    public void ResultMessageGetTest(){
        String shortUrl =  "我是非法短域名";
        Result<String> result = mainController.revertUrl(shortUrl);
        String message = result.getMessage();
        assertNotNull(message);
        assertEquals("短域名 URL 不合法，请检查输入的 URL ！", message);
    }

    /**
     * 入口类测试
     * @author : When6passBye
     * @date : 2021/7/19 下午5:00
     * @return : void
     */
    @Test
    public void IndexControllerTest() throws UnknownHostException {
        ModelAndView modelAndView = indexController.index();
        assertNotNull(modelAndView);
        String viewName = modelAndView.getViewName();
        assertEquals("index", viewName);
    }

    /**
     * 监控和验证类构造器测试
     * @author : When6passBye
     * @date : 2021/7/19 下午5:51
     * @return : void
     */
    @Test
    public void RamMonitorTest(){
        RamMonitor ramMonitor = new RamMonitor();
        assertNotNull(ramMonitor);
        Validator validator = new Validator();
        assertNotNull(validator);
    }



    //以下是cache 的相关测试

    @Test
    public void test1() {
        LruCacheV1<Integer, Integer> cache = new LruCacheV1<>(5);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        cache.put(4, 4);
        cache.put(5, 5);

        String s = cache.toString();
        assertNotNull(s);

        assertTrue(cache.get(1) == 1 && cache.get(2) == 2 && cache.get(4) == 4);
    }

    @Test
    public void test2() {
        LruCacheV1<Integer, Integer> cache = new LruCacheV1<>(5);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        cache.put(4, 4);
        cache.put(5, 5);

        cache.put(6, 6);

        assertEquals(cache.size(), 5);
    }

    @Test
    public void test3() {
        LruCacheV1<Integer, Integer> cache = new LruCacheV1<>(5);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        cache.put(4, 4);
        cache.put(5, 5);

        cache.put(6, 6);

        cache.put(7, 7);

        assertTrue(cache.get(4) == 4 && cache.get(6) == 6 && cache.get(7) == 7);
    }

    @Test
    public void test4() {
        LruCacheV1<Integer, Integer> cache = new LruCacheV1<>(5);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        cache.put(4, 4);
        cache.put(5, 5);

        cache.put(6, 6);
        cache.put(7, 7);

        assertTrue(cache.get(1) == null);
    }

    @Test
    public void test5() {
        LruCacheV1<Integer, Integer> cache = new LruCacheV1<>(3);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(1, 1);
        cache.put(3, 3);
        cache.put(4, 4);

        assertTrue(cache.get(1) == 1);
    }

    @Test
    public void test6() {
        LruCacheV2<Integer, Integer> cache = new LruCacheV2<>(1,5);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        cache.put(4, 4);
        cache.put(5, 5);

        String s = cache.toString();
        assertNotNull(s);

        assertTrue(cache.get(1) == 1 && cache.get(2) == 2 && cache.get(4) == 4);
    }

    @Test
    public void test7() {
        LruCacheV2<Integer, Integer> cache = new LruCacheV2<>(1,5);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        cache.put(4, 4);
        cache.put(5, 5);

        cache.put(6, 6);

        assertEquals(cache.size(), 5);
    }

    @Test
    public void test8() {
        LruCacheV2<Integer, Integer> cache = new LruCacheV2<>(1,5);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        cache.put(4, 4);
        cache.put(5, 5);

        cache.put(6, 6);

        cache.put(7, 7);

        assertTrue(cache.get(4) == 4 && cache.get(6) == 6 && cache.get(7) == 7);
    }

    @Test
    public void test9() {
        LruCacheV2<Integer, Integer> cache = new LruCacheV2<>(1, 5);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        cache.put(4, 4);
        cache.put(5, 5);

        cache.put(6, 6);
        cache.put(7, 7);

        assertTrue(cache.get(1) == null);
    }

    @Test
    public void test10() {
        LruCacheV2<Integer, Integer> cache = new LruCacheV2<>(1,3);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(1, 1);
        cache.put(3, 3);
        cache.put(4, 4);

        assertTrue(cache.get(1) == 1);
    }




}
