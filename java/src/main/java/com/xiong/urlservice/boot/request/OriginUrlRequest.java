package com.xiong.urlservice.boot.request;

import com.xiong.urlservice.boot.annotation.UrlValid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author: create by xiong
 * @version: v1.0
 * @description:
 * @date:2021/6/24 4:30 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("获取原链接-请求实体")
public class OriginUrlRequest {

    @UrlValid
    @ApiModelProperty(value = "短链接")
    private String shortUrl;
}
