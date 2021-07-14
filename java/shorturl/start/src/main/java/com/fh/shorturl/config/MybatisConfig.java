/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fh.shorturl.config;

import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 使用MapperScan批量扫描所有的Mapper接口
 *
 * @author paida 派哒 zeyu.pzy@alibaba-inc.com
 */
@Configuration
@MapperScan("com.fh.shorturl.mybatis.mapper")
public class MybatisConfig {

    /**
     * sequence主键，需要配置一个主键生成器
     * 配合实体类注解  @KeySequence 和  @TableId  type=INPUT
     * @return
     */
    @Bean
    public H2KeyGenerator h2KeyGenerator() {
        return new H2KeyGenerator();
    }
}
