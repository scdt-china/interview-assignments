package com.creolophus.liuyi.common.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

public class WebStart extends WebMvcConfigurationSupport implements
    ApplicationListener<ApplicationReadyEvent> {

  private static final Logger logger = LoggerFactory.getLogger(WebStart.class);

  /**
   * 自己的业务走这里
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    ApiInterceptor apiInterceptor = apiInterceptor();
    if (apiInterceptor != null) {
      logger.info("start addInterceptor with ApiInterceptor");
      registry.addInterceptor(apiInterceptor).addPathPatterns(apiInterceptor.getPathPatterns());
    }
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    registry.addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");
    registry.addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
    super.addResourceHandlers(registry);
    logger.info("start addResourceHandler for swagger");
  }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new LiuyiMappingJackson2HttpMessageConverter();
    ObjectMapper objectMapper = jackson2HttpMessageConverter.getObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    SimpleModule simpleModule = new SimpleModule();
    simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
    simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
    objectMapper.registerModule(simpleModule);
    JavaTimeModule javaTimeModule = new JavaTimeModule();
    javaTimeModule.addSerializer(LocalDateTime.class,
        new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    javaTimeModule.addDeserializer(LocalDateTime.class,
        new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    objectMapper.registerModule(javaTimeModule);

    jackson2HttpMessageConverter.setObjectMapper(objectMapper);

    GsonHttpMessageConverter gsonHttpMessageConverter = new LiuyiGsonHttpMessageConverter();

    Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    gsonHttpMessageConverter.setGson(gson);

    converters.add(jackson2HttpMessageConverter);
    converters.add(gsonHttpMessageConverter);
    logger.info(
        "start addMessageConverters with MappingJackson2HttpMessageConverter & GsonHttpMessageConverter");
  }

  @Bean
  @ConditionalOnMissingBean
  public ApiContextFilter apiContextFilter() {
    return new ApiContextFilter();
  }

  /**
   * 此对象用来校验每个业务的请求中都必须含有的 header & 封装每个线程的 ApiContext
   */
  @Bean
  @ConditionalOnMissingBean
  public ApiContextValidator apiContextValidator() {
    return new ApiContextValidator();
  }

  @Bean
  @ConditionalOnMissingBean
  public ApiInterceptor apiInterceptor() {
    return new ApiInterceptor();
  }

  protected NullArgumentConfirm getNullArgumentConfirm() {
    return new NullArgumentConfirm(false);
  }


  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    ConfigurableApplicationContext applicationContext = event.getApplicationContext();
    final RequestMappingHandlerAdapter bean = applicationContext
        .getBean(RequestMappingHandlerAdapter.class);
    List<HandlerMethodArgumentResolver> list = new ArrayList();
    list.add(getNullArgumentConfirm());
    list.addAll(bean.getArgumentResolvers());
    bean.setArgumentResolvers(list);
  }

}
