package com.scdtchina.sdns.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel("短域名存储接口请求类")
public class SaveRequest implements Serializable {

    private static final long serialVersionUID = 6918926768895687593L;

    @ApiModelProperty("长域名")
    @NotBlank
    private String normalUrl;
}
