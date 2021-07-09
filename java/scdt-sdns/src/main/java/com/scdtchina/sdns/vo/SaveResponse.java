package com.scdtchina.sdns.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@ApiModel("短域名存储接口响应类")
@AllArgsConstructor
@NoArgsConstructor
public class SaveResponse implements Serializable {

    private static final long serialVersionUID = 764395508838754843L;

    @ApiModelProperty("短域名")
    private String shortUrl;
}
