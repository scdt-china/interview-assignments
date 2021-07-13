package com.tzg157.demo.test.service;

import com.tzg157.demo.DemoTestBase;
import com.tzg157.demo.service.number.IdNumberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class SimpleIdNumberServiceTest extends DemoTestBase {

    @Resource(name = "simpleIdNumberService")
    private IdNumberService idNumberService;

    @Test
    public void testGetNextId(){
        Long id = idNumberService.getNextId();
        Assertions.assertNotNull(id);
    }
}
