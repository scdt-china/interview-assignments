package com.creolophus.liuyi.common.api;

import javax.servlet.http.HttpServletRequest;

/**
 * @author magicnana
 * @date 2020/10/10 4:02 PM
 */
public interface ApiAnnoHandler {

    boolean allow(Api api);

    void handle(HttpServletRequest request, Api api);
}
