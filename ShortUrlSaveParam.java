package com.zdkj.modler.shorturl.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author bihuiwen
 * @version 1.0
 * @description: TODO
 * @date 2021/7/5 下午10:45
 */
@Data
@ApiModel()
public class ShortUrlSaveParam implements Serializable {
    /**
     *长域名地址
     */
    @ApiModelProperty(value = "长网址",name = "longUrl",example = "http://www.baidu.com/djks/djksj/2394824389",required = true,allowEmptyValue = false)
    @NotNull(message = "长域名不能为空，请检查longUrl参数",groups = {save.class})
    @Pattern(regexp = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]",message = "长域名地址有误",groups = {save.class})
    private String longUrl;

    /**
     *生成短域名过期时间
     */
    @ApiModelProperty(value = "过期时间，单位为秒，-1为永久储存",name = "termOfValidity",example = "-1",required = true,allowEmptyValue = false)
    @NotNull(message = "过期时间不能为空",groups = {save.class})
    @Min(value = -1,message = "过期时间必须大于-1",groups = {save.class})
    private Long termOfValidity;

    /**
     *备注
     */
    @ApiModelProperty(value = "备注",name = "mark",example = "",required = false)
    private String mark;

    /**
     * 参数校验分组：保存
     */
    public @interface save {
    }
}
