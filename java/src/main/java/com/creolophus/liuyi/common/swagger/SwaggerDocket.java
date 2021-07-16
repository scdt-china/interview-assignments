package com.creolophus.liuyi.common.swagger;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;

/**
 * @author magicnana
 * @date 2019/7/26 下午4:26
 */
public class SwaggerDocket {

  public static Parameter header(String name, boolean require, String defaultValue) {
    ParameterBuilder parameter = new ParameterBuilder();
    return parameter.name(name).description("")
        .defaultValue(defaultValue)
        .modelRef(new ModelRef("string")).parameterType("header")
        .required(require).build();
  }
}
