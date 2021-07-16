package com.creolophus.liuyi.common.api;

import com.creolophus.liuyi.common.exception.HttpStatusException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @author magicnana
 * @date 2020/10/10 4:03 PM
 */
@Component
public class ApiAnno4InterHandler implements ApiAnnoHandler {

    private static final Logger logger = LoggerFactory.getLogger(ApiAnno4InterHandler.class);

    @Override
    public boolean allow(Api api) {
        if (api == null) {
            return false;
        }
        return StringUtils.isNotBlank(api.scope()) && api.scope().equalsIgnoreCase(Api.SCOPE_INTER);
    }

    @Override
    public void handle(HttpServletRequest request, Api api) {
        String header = request.getHeader(Api.HEADER_INTER_KEY);
        if (StringUtils.isNotBlank(header) && header.equals(Api.HEADER_INTER_VAL)) {
            long userId = 0L;
            ApiContext.getContext().setApi(api);
            ApiContext.getContext().setToken(header);
            ApiContext.getContext().setUserId(userId);
            if (logger.isDebugEnabled()) {
                logger.debug("token:{}", header);
                logger.debug("{}:{}", userId, "NONE");
            }
        } else {
            logger.error("还没授权" + request.getRequestURI());
            throw new HttpStatusException(HttpStatus.UNAUTHORIZED);
        }

    }
}
