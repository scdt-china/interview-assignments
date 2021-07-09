package com.scdtchina.sdns.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel("短域名读取接口请求类")
public class FindRequest implements Serializable {

    private static final long serialVersionUID = -7086902532881552864L;

    @ApiModelProperty("短域名")
    @NotBlank
    private String shortUrl;
}
