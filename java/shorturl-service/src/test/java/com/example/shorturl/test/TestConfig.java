package com.example.shorturl.test;

import com.example.shorturl.controller.ShorturlController;
import com.example.shorturl.vos.LongurlReqVo;
import com.example.shorturl.vos.ShorturlReqVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试单元
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = com.example.shorturl.test.TestConfig.class)
@SpringBootApplication
@ComponentScan("com.example.*")
public class TestConfig {

	@Autowired
	private ShorturlController controller;

	@Test
	public void testSys() {
		ShorturlReqVo reqVo = new ShorturlReqVo();
		LongurlReqVo reqlongVo = new LongurlReqVo();
		reqVo.setUrl("www.baidu.com");
		controller.getShorturl(reqVo);
		reqVo.setUrl("www.baidu.com");
		controller.getShorturl(reqVo);
		reqlongVo.setUrl("oIpU0");
		controller.getLongurl(reqlongVo);
	}
}