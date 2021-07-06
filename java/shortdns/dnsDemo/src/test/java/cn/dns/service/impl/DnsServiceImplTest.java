package cn.dns.service.impl;

import cn.dns.service.inter.DnsService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DnsServiceImplTest {
    @Resource(name = "dnsService")
    private DnsService dnsService;
    @Test
    public void getDns() {
        String sort = dnsService.getShortDns("https://cnsdemo.cn/serolsikklsdkflsdflslflslflsflslfldsflsdkfkdsk");
        Assert.assertNotNull( sort );
        System.out.println("sort:"+sort);
        String longDns =dnsService.getLongDns(sort);
        Assert.assertNotNull( longDns );
        System.out.println(longDns);
    }


    @Test
    public void checkDns() {
        String sort = dnsService.getShortDns("");
        Assert.assertNotNull( sort );
        System.out.println("sort:"+sort);
        String longDns =dnsService.getLongDns(sort);
        Assert.assertNotNull( longDns );
        System.out.println(longDns);
        assertTrue( true );
    }

}
