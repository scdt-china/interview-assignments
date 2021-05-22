package com.example.shorturl.configuration;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.adapter.reactor.ContextConfig;
import com.alibaba.csp.sentinel.adapter.reactor.EntryConfig;
import com.alibaba.csp.sentinel.adapter.reactor.SentinelReactorTransformer;
import com.alibaba.csp.sentinel.adapter.spring.webflux.SentinelWebFluxFilter;
import com.alibaba.csp.sentinel.adapter.spring.webflux.callback.WebFluxCallbackManager;
import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.example.shorturl.utils.IpUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 限流配置
 * @author hcq
 */
@Configuration
public class SentinelConfig {
	/**
	 * 定义sentinel限流longToShort用的资源
	 */
	public static final String LONG_TO_SHORT_RES = "longToShort";
	/**
	 * 定义sentinel限流shortToLong用的资源
	 */
	public static final String SHORT_TO_LONG_RES = "shortToLong";
	/**
	 * 定义sentinel限流longToShort的每秒限制数量(按IP)
	 */
	public static final int LONG_TO_SHORT_LIMIT = 5;
	/**
	 * 定义sentinel限流shortToLong的限制数量(按IP)
	 */
	public static final int SHORT_TO_LONG_LIMIT = 10;

	/**
	 * AOP使sentinel注解生效
	 * @return SentinelResourceAspect
	 */
	@Bean
	public SentinelResourceAspect sentinelResourceAspect() {
		return new SentinelResourceAspect();
	}

	/**
	 * 自定义Sentinel过滤器
	 * @return WebFilter
	 */
	@Bean
	@Order(-2)
	public WebFilter sentinelWebFluxFilter() {
		return new SentinelWebFluxFilter() {
			@Override
			public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
				return chain.filter(exchange).transform(this.buildSentinelTransformer(exchange));
			}

			/**
			 * build SentinelReactorTransformer
			 * @param exchange ServerWebExchange
			 * @return SentinelReactorTransformer
			 */
			private SentinelReactorTransformer<Void> buildSentinelTransformer(ServerWebExchange exchange) {
				ServerHttpRequest serverHttpRequest = exchange.getRequest();
				String path = exchange.getRequest().getPath().value();
				String finalPath = Optional.ofNullable(WebFluxCallbackManager.getUrlCleaner())
						.map((f) -> f.apply(exchange, path)).orElse(path);
				String origin = IpUtils.getIp(serverHttpRequest);
				return new SentinelReactorTransformer(new EntryConfig(finalPath, EntryType.IN,
						new ContextConfig(finalPath, origin)));
			}
		};
	}

	/**
	 * 自定义异常处理器
	 * @return WebExceptionHandler
	 */
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public WebExceptionHandler sentinelBlockExceptionHandler() {
		return new WebExceptionHandler() {
			private Mono<Void> writeResponse(ServerResponse response, ServerWebExchange exchange) {
				ServerHttpResponse serverHttpResponse = exchange.getResponse();
				serverHttpResponse.getHeaders().add("Content-Type",
						"application/json;charset=UTF-8");
				byte[] result = "{\"code\":429,\"message\":\"请求超过最大数，请稍后再试\"}"
						.getBytes(StandardCharsets.UTF_8);
				DataBuffer buffer = serverHttpResponse.bufferFactory().wrap(result);
				return serverHttpResponse.writeWith(Mono.just(buffer));
			}

			@Override
			public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {
				if (serverWebExchange.getResponse().isCommitted()) {
					return Mono.error(throwable);
				}
				if (!BlockException.isBlockException(throwable)) {
					return Mono.error(throwable);
				}
				return handleBlockedRequest(serverWebExchange, throwable)
						.flatMap(response -> writeResponse(response, serverWebExchange));
			}

			private Mono<ServerResponse> handleBlockedRequest(
					ServerWebExchange exchange, Throwable throwable) {
				return WebFluxCallbackManager.getBlockHandler().handleRequest(exchange, throwable);
			}
		};
	}

	/**
	 * 限流配置
	 */
	@PostConstruct
	private static void initFlowQpsRule() {
		List<FlowRule> rules = new ArrayList<>();
		FlowRule rule = new FlowRule(LONG_TO_SHORT_RES);
		//定义了每秒最多接收请求个数
		rule.setCount(LONG_TO_SHORT_LIMIT);
		rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
		rules.add(rule);

		rule = new FlowRule(SHORT_TO_LONG_RES);
		//定义了每秒最多接收请求个数
		rule.setCount(SHORT_TO_LONG_LIMIT);
		rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
		rules.add(rule);
		FlowRuleManager.loadRules(rules);
	}
}
