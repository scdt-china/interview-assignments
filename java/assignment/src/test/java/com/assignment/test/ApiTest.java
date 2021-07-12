package com.assignment.test;

import com.sequoiacapital.assignment.StartAssignmentApplication;
import com.sequoiacapital.assignment.common.resp.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试
 *
 * @Author: xin.wu
 * @Date: Created in 2021/6/24 18:01
 * @Version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartAssignmentApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String key;

    @Test
    public void test()  {
        String url = "http://127.0.0.1:"+port;
        Result<String> res = restTemplate.getForObject(url+"/assignment/v1/change?url={1}",Result.class,"http://www.test.com/abc/xyz/123");
        key = res.getData();
        Result<String> res2 = restTemplate.getForObject(url+"/assignment/v1/restore/"+key,Result.class);
        System.out.println(res2.getData());
    }



}
