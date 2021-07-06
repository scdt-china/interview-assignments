package com.example.demo;

import com.example.demo.service.UrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    UrlService urlService;
    @Test
    void saveTest() {
        String save = urlService.save("https://www.baidu.com/");
        System.out.println(save);
    }
    @Test
    void restoreUrlTest() {
        String save = urlService.restoreUrl("fmMry2");
        System.out.println(save);
    }
}
