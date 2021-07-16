package com.creolophus.liuyi.common.base;

import com.creolophus.liuyi.common.api.ApiContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author magicnana
 * @date 2019/5/20 下午2:31
 */
public class AbstractController {

  protected String currentIP() {
    return ApiContext.getContext().getIp();
  }

  protected HttpServletRequest currentRequest() {
    return ApiContext.getContext().getRequest();
  }

  protected String currentToken() {
    return ApiContext.getContext().getToken();
  }

  protected long currentUserId() {
    return ApiContext.getContext().getUserId();
  }
}
