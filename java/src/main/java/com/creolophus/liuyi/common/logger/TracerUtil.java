package com.creolophus.liuyi.common.logger;

import brave.Span;
import brave.Tracer;
import org.springframework.cloud.sleuth.util.SpanNameUtil;

/**
 * @author magicnana
 * @date 2019/10/14 下午3:01
 */
public class TracerUtil {

  private static final String CLASS_KEY = "class";
  private static final String METHOD_KEY = "method";

  private Tracer tracer;

  public TracerUtil(Tracer tracer) {
    this.tracer = tracer;
  }

  public Tracer.SpanInScope begin(String methodName, String className) {

    //TODO ... 新版本这个方法过时了,需要看看源码

    String spanName = SpanNameUtil.toLowerHyphen(methodName);
    Span span = startOrContinueRenamedSpan(spanName);
    span.tag(CLASS_KEY, className);
    span.tag(METHOD_KEY, methodName);
    Tracer.SpanInScope scope = this.tracer.withSpanInScope(span.start());
    return scope;
  }

  public Span startOrContinueRenamedSpan(String spanName) {
//        return this.tracer.newTrace().name(spanName);
    Span currentSpan = this.tracer.currentSpan();
    if (currentSpan != null) {
      return currentSpan.name(spanName);
    }
    return this.tracer.nextSpan().name(spanName);
  }

}
