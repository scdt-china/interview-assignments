package com.creolophus.liuyi.common.api;

public class ApiContextLocal extends InheritableThreadLocal<ApiContext> {

    /**
     * 上下文信息
     */
    private static final ApiContextLocal WEB_CONTEXT_LOCAL = new ApiContextLocal() {
        @Override
        protected ApiContext initialValue() {
            return new ApiContext();
        }
    };

    /**
     * 获得当前线程对象
     */
    public static ApiContextLocal getInstance() {
        return WEB_CONTEXT_LOCAL;
    }
}
