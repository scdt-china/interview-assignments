package com.tzg157.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

public class DemoTestBase {

    protected static final String SHORT_REQUEST_URL = "https://scdt.cn/1";
    protected static final String LONG_REQUEST_URL = "https://www.baidu.com";
    protected static final String SHORT_REQUEST_URL_2 = "https://scdt.cn/2";
    protected static final String LONG_REQUEST_URL_2 = "https://www.google.com";

    protected String convertToJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
