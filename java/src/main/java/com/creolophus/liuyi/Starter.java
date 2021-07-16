package com.creolophus.liuyi;

import com.creolophus.liuyi.common.api.WebStart;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author magicnana
 * @date 2021/7/13 16:30
 */
@SpringBootApplication(scanBasePackages = "com.creolophus")
@EnableSwagger2
public class Starter extends WebStart {

  public static void main(String[] args) {
    SpringApplication.run(Starter.class, args);
  }

}
