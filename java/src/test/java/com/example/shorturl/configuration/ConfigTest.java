package com.example.shorturl.configuration;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.server.WebExceptionHandler;
import org.springframework.web.server.WebFilter;
import springfox.documentation.spring.web.plugins.Docket;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("配置类测试")
class ConfigTest {
	private AnnotationConfigApplicationContext context;

	@BeforeEach
	public void init() {
		context = new AnnotationConfigApplicationContext();
	}

	@AfterEach
	public void reset() {
		context.close();
	}

	@Test
	void sentinelConfig() {
		context.register(SentinelConfig.class);
		context.refresh();
		assertNotNull(context.getBean(SentinelResourceAspect.class));
		assertNotNull(context.getBean(WebExceptionHandler.class));
		assertNotNull(context.getBean(WebFilter.class));
	}

	@Test
	void swaggerConfig() {
		context.register(SwaggerConfig.class);
		context.refresh();
		assertNotNull(context.getBean(Docket.class));
	}

	@Test
	void swaggerUiWebFluxConfig() {
		context.register(SwaggerUiWebFluxConfig.class);
		context.refresh();
		SwaggerUiWebFluxConfig config = context.getBean(SwaggerUiWebFluxConfig.class);
		assertNotNull(config);
	}
}
