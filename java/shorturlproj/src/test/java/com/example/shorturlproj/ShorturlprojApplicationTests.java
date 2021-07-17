package com.example.shorturlproj;

import com.example.shorturlproj.controller.ShortUrlProjController;
import com.example.shorturlproj.service.ShortUrlTransformService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShorturlprojApplicationTests {
    @Autowired
    public ShortUrlProjController shortUrlProjController;
    @Autowired
    public ShortUrlTransformService shortUrlTransformService;
    private char[] digitsTest = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
    };

    @Test
    public void getShortUrl() throws ExecutionException {
        String shortUrl1 = shortUrlProjController.getShortUrlTest("http://www.asdasdasd.com/asdasdasd/asdasdasd");
        String shortUrl2 = shortUrlProjController.getShortUrlTest("http://www.asdasdasd.com/asdasdasd/asdasdasd");
        String shortUrl3 = shortUrlProjController.getShortUrlTest("http://www.asdasdasd.com/asdasdasd/asd");
        String shortUrl4 = shortUrlProjController.getShortUrlTest("");
        String shortUrl5 = shortUrlProjController.getShortUrlTest(null);
        String longUrl1 = shortUrlProjController.getLongUrlTest("http://dwz.cn/t/0");
        String longUrl2 = shortUrlProjController.getLongUrlTest("http://dwz.cn/t/1");
        String longUrl3 = shortUrlProjController.getLongUrlTest("");
        String longUrl4 = shortUrlProjController.getLongUrlTest(null);
        String longUrl5 = shortUrlProjController.getLongUrlTest("http://dwz.null/t23/0");
        String longUrl6 = shortUrlProjController.getLongUrlTest("http://dwz.cn/t/0/");
        String longUrl7 = shortUrlProjController.getLongUrlTest("http://dwz.cn/t/xxx");
        String longUrl8 = shortUrlProjController.getLongUrlTest("http://dwz.cn/t/2");
        //String shortUrl1 = shortUrlTransformService.getShortUrlFromLongUrl("http://www.asdasdasd.com/asdasdasd/asdasdasd");
        //String shortUrl2 = shortUrlTransformService.getShortUrlFromLongUrl("http://www.asdasdasd.com/asdasdasd/asdasdasd");
        //String shortUrl3 = shortUrlTransformService.getShortUrlFromLongUrl("http://www.asdasdasd.com/asdasdasd/asd");
        //String shortUrl4 = shortUrlTransformService.getShortUrlFromLongUrl("");
        //String shortUrl5 = shortUrlTransformService.getShortUrlFromLongUrl(null);
        //String longUrl1 = shortUrlTransformService.getLongUrlFromShortUrl("http://dwz.cn/t/0");
        //String longUrl2 = shortUrlTransformService.getLongUrlFromShortUrl("http://dwz.cn/t/1");
        //String longUrl3 = shortUrlTransformService.getLongUrlFromShortUrl("");
        //String longUrl4 = shortUrlTransformService.getLongUrlFromShortUrl(null);
        //String longUrl5 = shortUrlTransformService.getLongUrlFromShortUrl("http://dwz.null/t23/0");
        //String longUrl6 = shortUrlTransformService.getLongUrlFromShortUrl("http://dwz.cn/t/0/");
        //String longUrl7 = shortUrlTransformService.getLongUrlFromShortUrl("http://dwz.cn/t/xxx");
        Assert.assertEquals("http://dwz.cn/t/0", shortUrl1);
        Assert.assertEquals("http://dwz.cn/t/1", shortUrl2);
        Assert.assertEquals("http://dwz.cn/t/2", shortUrl3);
        Assert.assertEquals("输入长域名为空", shortUrl4);
        Assert.assertEquals("输入长域名为空", shortUrl5);
        Assert.assertEquals("http://www.asdasdasd.com/asdasdasd/asdasdasd", longUrl1);
        Assert.assertEquals("http://www.asdasdasd.com/asdasdasd/asdasdasd", longUrl2);
        Assert.assertEquals("http://www.asdasdasd.com/asdasdasd/asd", longUrl8);
        Assert.assertEquals("输入短域名为空", longUrl3);
        Assert.assertEquals("输入短域名为空", longUrl4);
        Assert.assertEquals("错误的短域名", longUrl5);
        Assert.assertEquals("错误的短域名", longUrl6);
        Assert.assertEquals("链接已失效", longUrl7);
        for(long i = 0; i < 59L; i++){
           shortUrlProjController.getShortUrlTest("http://www.asdasdasd.com/asdasdasd/asd"+String.valueOf(i));
        }
        String shortUrl6 = shortUrlProjController.getShortUrlTest("http://www.asdasdasd.com/asdasdasd/asd"+String.valueOf(60));
        Assert.assertEquals("http://dwz.cn/t/10", shortUrl6);
    }

}
