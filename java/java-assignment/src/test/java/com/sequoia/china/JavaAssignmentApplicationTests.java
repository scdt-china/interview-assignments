package com.sequoia.china;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.sequoia.china.common.ResponseData;
import com.sequoia.china.container.DomainNameContainer;
import com.sequoia.china.controller.DomainNameConvertController;
import com.sequoia.china.util.IdUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.LinkedList;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
class JavaAssignmentApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    DomainNameContainer container;

    /**
     * 测试两个接口是否正常
     * @throws Exception
     */
    @Test
    void testDomainNameConvert() throws Exception {
        //长域名转短域名
        String longDomainName="https://baike.baidu.com/item/红杉资本/9915610?fr=aladdin";
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/domainNameconvert/longToShort")
                .param("longDomainName",longDomainName))
                .andReturn();
        String shortDomainName = mvcResult.getResponse().getContentAsString();
        if (StrUtil.isEmpty(shortDomainName)){
            System.out.println("返回为空");
            return;
        }
        ResponseData<String> responseData = JSONUtil.toBean(shortDomainName, ResponseData.class);
        if (!responseData.isSuccess()){
            System.out.println("返回异常"+responseData.getErrorCode()+"-"+responseData.getErrorMsg());
            return;
        }
        String data = responseData.getData();
        System.out.println("短域名："+data);
        //短域名转长域名
        MvcResult mvcResult2=mockMvc.perform(MockMvcRequestBuilders.post("/domainNameconvert/shortToLong")
                .param("shortDomainName",data))
                .andReturn();
        String longDomainName2 = mvcResult2.getResponse().getContentAsString(Charset.forName("UTF-8"));
        if (StrUtil.isEmpty(longDomainName2)){
            System.out.println("返回为空");
            return;
        }
        ResponseData<String> responseData2 = JSONUtil.toBean(longDomainName2, ResponseData.class);
        if (!responseData2.isSuccess()){
            System.out.println("返回异常"+responseData2.getErrorCode()+"-"+responseData2.getErrorMsg());
            return;
        }
        String data1 = responseData2.getData();
        System.out.println("长域名："+data1);
        System.out.println("与原长域名比较结果："+(longDomainName.equals(data1)));
    }


    /**
     * 测试LRU是否生效
     * @throws Exception
     */
    @Test
    void testLRU() throws Exception {
        //先初始化10条数据
        for (int i = 0; i < 10; i++) {
            String longDomainName="https://baike.baidu.com/item/红杉资本/9915610?fr=aladdin"+i;
            MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/domainNameconvert/longToShort")
                    .param("longDomainName",longDomainName))
                    .andReturn();
        }
        //再访问第二条数据，看第二条数据的位置
        String longDomainName="https://baike.baidu.com/item/红杉资本/9915610?fr=aladdin"+2;
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/domainNameconvert/longToShort")
                .param("longDomainName",longDomainName))
                .andReturn();
        String shortDomainName = mvcResult.getResponse().getContentAsString();
        ResponseData<String> responseData = JSONUtil.toBean(shortDomainName, ResponseData.class);
        String data = responseData.getData();
        System.out.println("短域名："+data);

        //上面总共10条数据，将配置文件中container-max-count改为10，测试是否会删除头部节点
        String longDomainName2="https://baike.baidu.com/item/红杉资本/9915610?fr=aladdin"+111;
        MvcResult mvcResult2=mockMvc.perform(MockMvcRequestBuilders.post("/domainNameconvert/longToShort")
                .param("longDomainName",longDomainName2))
                .andReturn();

        //在访问第二条数据，使用shortToLong访问，第二条数据应该在最后
        MvcResult mvcResult3=mockMvc.perform(MockMvcRequestBuilders.post("/domainNameconvert/shortToLong")
                .param("shortDomainName",data))
                .andReturn();
        System.out.println("map容量："+container.shortToLongMap.size()+"-"+container.longToShortMap.size());
        for (String str:container.linkedList) {
            System.out.println(str);
        }
    }

}
