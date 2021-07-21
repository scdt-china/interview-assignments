package com.homework.tinyurl;

import com.homework.tinyurl.utils.UrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Deacription
 * @Author zhangjun
 * @Date 2021/7/20 9:51 下午
 **/
@Slf4j
@SpringBootTest
public class UrlUtilTest {

    @Test
    public void isValidationUrlTest() throws Exception {
        UrlUtil.isValidationUrl("");
    }
}
