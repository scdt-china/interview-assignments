package com.sequoia.shorturl;

/**
 *
 * 所有测试
 *
 * @Author xj
 *
 * @Date 2021/6/30
 *
 * @version v1.0.0
 *
 */

import com.sequoia.shorturl.controller.ShortUrlControllerTest;
import com.sequoia.shorturl.service.ShortUrlServiceImplTest;
import com.sequoia.shorturl.util.ShortCodeUtilTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
/**
 * 测试用例集，集成测试所有单元测试
 * 不用写代码,只需要注解即可(在SuiteClasses中配置测试类)
 *
 * @Author xj
 *
 * @Date 2021/6/30
 *
 * @version v1.0.0
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ShortCodeUtilTest.class, ShortUrlServiceImplTest.class, ShortUrlControllerTest.class})
public class SuiteExecuteTests {

}
