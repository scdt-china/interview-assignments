package com.domain;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @ClassName:BaseSpringBootTest
 * @Description:TODO
 * @author: wangkui DJ009697
 * @date:2021/07/12 下午7:50
 * @version:1.0
 */
@SpringBootTest
@WebAppConfiguration
public class BaseSpringBootTest {
    protected static Logger logger = LoggerFactory.getLogger(BaseSpringBootTest.class);

    @BeforeAll
    public static void init() {
        logger.info("开始测试...");
    }

    @AfterAll
    public static void after() {
        logger.info("测试结束...");
    }
}
