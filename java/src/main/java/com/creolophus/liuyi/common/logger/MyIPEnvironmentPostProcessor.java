package com.creolophus.liuyi.common.logger;

import com.creolophus.liuyi.common.api.MdcUtil;
import com.creolophus.liuyi.common.env.AbstractEnvironmentPostProcessor;
import com.creolophus.liuyi.common.util.IPUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author magicnana
 * @date 2019/9/26 下午3:25
 */
public class MyIPEnvironmentPostProcessor extends AbstractEnvironmentPostProcessor {

    private static final String PROPERTY_SOURCE_NAME = "defaultProperties";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment,
        SpringApplication application) {
        Map<String, Object> map = new HashMap();
        map.put(MdcUtil.MDC_IP, IPUtil.getLocalIP());
        super.addOrReplace(environment.getPropertySources(), map, PROPERTY_SOURCE_NAME);
    }
}
