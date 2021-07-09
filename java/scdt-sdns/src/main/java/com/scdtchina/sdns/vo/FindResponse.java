package com.scdtchina.sdns.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@ApiModel("短域名读取接口响应类")
@AllArgsConstructor
@NoArgsConstructor
public class FindResponse implements Serializable {

    private static final long serialVersionUID = 1519183108494610249L;

    @ApiModelProperty("长域名")
    private String normalUrl;
}
