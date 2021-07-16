package com.creolophus.liuyi.common.api;

import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;

/**
 * @author magicnana
 * @date 2019/9/19 下午5:32
 */
public class NullArgumentConfirm extends RequestParamMethodArgumentResolver {

    public NullArgumentConfirm(boolean useDefaultResolution) {
        super(useDefaultResolution);
    }

    public NullArgumentConfirm(ConfigurableBeanFactory beanFactory, boolean useDefaultResolution) {

        super(beanFactory, useDefaultResolution);
    }


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(RequestParam.class)) {
            if (Map.class.isAssignableFrom(parameter.nestedIfOptional().getNestedParameterType())) {
                RequestParam requestParam = parameter.getParameterAnnotation(RequestParam.class);
                return (requestParam != null && org.springframework.util.StringUtils
                    .hasText(requestParam.name()));
            } else {
                return true;
            }
        } else {
            return false;
        }
    }


    /**
     * 如果上行参数是""，并且类型是String，那么转换为null
     */
    @Override
    protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request)
        throws Exception {
        Object arg = super.resolveName(name, parameter, request);
        if (arg == null) {
            return null;
        }
        if (arg instanceof String && StringUtils.isBlank(arg.toString())) {
            return null;
        }

        return arg;
    }

}
