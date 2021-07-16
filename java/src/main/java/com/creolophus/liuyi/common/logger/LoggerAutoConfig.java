package com.creolophus.liuyi.common.logger;

import brave.Tracer;
import brave.servlet.TracingFilter;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.sleuth.instrument.scheduling.SleuthSchedulingProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jinsong
 */
@Configuration
@EnableConfigurationProperties(SleuthSchedulingProperties.class)
public class LoggerAutoConfig {

  private static final Logger logger = LoggerFactory.getLogger(LoggerAutoConfig.class);

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnProperty(value = "liuyi.global.model", havingValue = "debug", matchIfMissing = true)
  public LoggerAspect loggerAspect() {
    logger.info("start... logger");
    return new LoggerAspect();
  }

  @Bean
  @ConditionalOnClass(TracingFilter.class)
  @ConditionalOnMissingBean
  public EntryTraceAspect traceAspect(Tracer tracer,
      SleuthSchedulingProperties sleuthSchedulingProperties) {
    logger.info("start... trace logger");
    return new EntryTraceAspect(tracer,
        Pattern.compile(sleuthSchedulingProperties.getSkipPattern()));
  }

  @Bean
  @ConditionalOnMissingBean
  public TracerUtil tracerUtil(Tracer tracer) {
    return new TracerUtil(tracer);
  }

}
