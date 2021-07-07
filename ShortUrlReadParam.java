package com.zdkj.modler.shorturl.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author bihuiwen
 * @version 1.0
 * @description: TODO
 * @date 2021/7/5 下午10:14
 */
@Data
public class ShortUrlReadParam {

    @ApiModelProperty(value = "短网址",name = "shortUrl",required = true,allowEmptyValue = false)
    @NotNull(message = "短域名不能为空，请检查shortUrl参数",groups = {read.class})
    @Pattern(regexp = "http://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]",message = "短域名地址有误。",groups = {read.class})
    private String shortUrl;

    /**
     * 参数校验分组：保存
     */
    public @interface read {
    }

}
