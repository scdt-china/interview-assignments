package com.example.assignment;

import com.example.assignment.facade.UrlAPIFacde;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AssignmentApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class AssignmentApplicationTests {

    @Autowired
    private UrlAPIFacde urlAPIFacde;

    @Test
    void testUrl() {

        String request = "http://www.baidu.com/asfasdfasdfw99f3falskijdf";
        System.out.println("test request================================");
        System.out.println(request);

        String shortUrl = urlAPIFacde.getShortUrl(request);
        System.out.println("test getShortUrl================================");
        System.out.println(shortUrl);
        assert shortUrl.length() <= 8;

        String longUrl = urlAPIFacde.getLongUrl(shortUrl);

        System.out.println("test getLongUrl================================");
        System.out.println(longUrl);
        assert request.equals(longUrl);
    }

}
