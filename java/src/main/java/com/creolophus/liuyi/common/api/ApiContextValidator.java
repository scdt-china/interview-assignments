package com.creolophus.liuyi.common.api;

import com.creolophus.liuyi.common.util.IPUtil;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author magicnana
 * @date 2019/8/12 上午10:58
 */
public class ApiContextValidator {


    private static final Logger logger = LoggerFactory.getLogger(ApiContextValidator.class);

    public void cleanContext() {

        ApiContext apiContext = ApiContext.getContext();

        apiContext.setUserId(0);
        apiContext.setUserAgent(null);
        apiContext.setIp(null);
        apiContext.setToken(null);
        apiContext.setRequest(null);
        apiContext.getExt().clear();
        apiContext.setApiResult(null);
        ApiContext.releaseContext();

    }

    protected String getDefaultExt() {
        return MdcUtil.MDC_DEFAULT;
    }

    public String[] ignoringAntMatchers() {
        return new String[]{};
    }

    public void initContext() {
        initContext(null);
    }

    public void initContext(HttpServletRequest request) {

        MdcUtil.setExt(getDefaultExt());
        MdcUtil.setUri();
        MdcUtil.setMethod("ApiContext");

        if (request != null) {
            ApiContext.getContext().setRequest(request);
            ApiContext.getContext().setIp(IPUtil.getRemoteIP(request));
            ApiContext.getContext().setUserAgent(request.getHeader("User-Agent"));

            MdcUtil.setUri(request.getRequestURI());
        }

    }

    public void setApiResult(Object obj) {
        ApiContext.getContext().setApiResult(obj);
    }

}
