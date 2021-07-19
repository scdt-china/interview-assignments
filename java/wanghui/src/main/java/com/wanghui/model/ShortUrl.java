package com.wanghui.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wanghui
 * @title API返回对象
 * @Date 2021-07-17 15:36
 * @Description
 */
@ApiModel("短域名对象")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrl  implements Serializable {

    private static final long serialVersionUID = 8595399514967770543L;

    /**
     * 短域名
     */
    @ApiModelProperty("短域名")
    private String shortUrl;

    /**
     * 长域名
     */
    @ApiModelProperty(value = "长域名", example = "www")
    private String longUrl;

    /**
     * 状态码
     * 请求响应成功：{@link com.wanghui.utils.CommonConstants#CODE_SUCCESS}
     * 请求服务异常: {@link com.wanghui.utils.CommonConstants#CODE_SERVICE_UNAVAILABLE}
     * 请求未发现:  {@link com.wanghui.utils.CommonConstants#CODE_LONG_URL_NOT_FOUND}
     */
    @ApiModelProperty(value = "code", example = "200")
    private Integer code;

    @ApiModelProperty(value = "提示信息", example = "成功")
    private String msg;

}
