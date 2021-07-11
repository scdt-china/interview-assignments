package com.csc.shorturl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShorturlApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testgetsurlhttp() {
        String requestResult = this.restTemplate.getForObject("http://127.0.0.1:" + port + "/getsurl?url=www.sina.com",
                String.class);
        Assertions.assertThat(requestResult).contains("网址必须以http或https开头");
    }

    @Test
    public void testgetsurlno() {
        String requestResult = this.restTemplate.getForObject("http://127.0.0.1:" + port + "/getsurl",
                String.class);
        Assertions.assertThat(requestResult).contains("参数不能为空");
    }
    @Test
    public void testgetsurl() {
        String requestResult = this.restTemplate.getForObject("http://127.0.0.1:" + port + "/getsurl?url=https://www.sina.com",
                String.class);
        System.out.println(requestResult);
    }

    @Test
    public void testgetlurl() {

        String requestResult = this.restTemplate.getForObject("http://127.0.0.1:" + port + "/getlurl?url=1",
                String.class);
        Assertions.assertThat(requestResult).contains("没有此地址对应长地址");
    }

    @Test
    public void testgetlurlno() {
        String requestResult = this.restTemplate.getForObject("http://127.0.0.1:" + port + "/getlurl",
                String.class);
        Assertions.assertThat(requestResult).contains("参数不能为空");
    }

    @Test
    public void testgetlurl2() {
        String requestResults = this.restTemplate.getForObject("http://127.0.0.1:" + port + "/getsurl?url=http://123",
                String.class);
        System.out.println(requestResults);
        String requestResultl = this.restTemplate.getForObject("http://127.0.0.1:" + port + "/getlurl?url="+requestResults,
                String.class);
        System.out.println(requestResultl);
        Assertions.assertThat(requestResultl ).contains("http://123");
    }

}

