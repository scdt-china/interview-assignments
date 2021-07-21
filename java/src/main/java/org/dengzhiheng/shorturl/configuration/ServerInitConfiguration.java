package org.dengzhiheng.shorturl.configuration;


import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.YitIdHelper;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @ClassName ServerInitConfiguration
 * @Description: 上下文管理，初始化一些需要的bean
 * @Author gravel
 * @Date 2020/1/29
 * @Version V1.0
 **/

@Component
public class ServerInitConfiguration implements ApplicationListener<WebServerInitializedEvent> {
    private int serverPort;

    public String getUrl() throws UnknownHostException {
        InetAddress address;
        address = InetAddress.getLocalHost();
        return "http://" + address.getHostAddress() + ":" + this.serverPort;
    }

    //初始化加载必备的
    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.serverPort = event.getWebServer().getPort();
        IdGeneratorOptions options = new IdGeneratorOptions((short) 1);
        YitIdHelper.setIdGenerator(options);
    }

}
