package com.sequoia.shorturl.filter;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
/**
 *
 * 防Xss过滤器[包装器]
 *
 * @Author xj
 *
 * @Date 2021/06/28
 *
 * @version v1.0.0
 *
 */
public class XssFilterWrapper extends HttpServletRequestWrapper {

    public XssFilterWrapper(HttpServletRequest request) {
        super(request);
    }

    /**
     * 对数组参数进行特殊字符过滤
     */
    @Override
    public String[] getParameterValues(String name) {

        String[] values = super.getParameterValues(name);
        String[] newValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            newValues[i] = HtmlUtils.htmlEscape(values[i]);//spring的HtmlUtils进行转义
        }
        return newValues;
    }
}
