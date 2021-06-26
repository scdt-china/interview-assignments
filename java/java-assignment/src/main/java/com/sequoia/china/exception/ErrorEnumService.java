package com.sequoia.china.exception;

/**
 * @Description 异常枚举接口类
 * @Author helichao
 * @Date 2021/6/25 10:06 下午
 */
public interface ErrorEnumService {

    /**
     * 获取异常编码
     *
     * @return 异常编码
     */
    String getCode();

    /**
     * 获取异常提示信息
     *
     * @return 异常提示信息
     */
     String getMsg();
}
