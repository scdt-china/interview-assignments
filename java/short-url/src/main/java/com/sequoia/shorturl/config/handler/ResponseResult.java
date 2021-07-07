package com.sequoia.shorturl.config.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 统一ResponseBody返回值
 *
 * @Author xj
 *
 * @Date 2021/06/27
 *
 * @version v1.0.0
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseResult {
    private String result;
    private String errorCode;
    private String message;
    private Object data;

    public ResponseResult(String result, Object data) {
        this.result = result;
        this.data = data;
    }
}
