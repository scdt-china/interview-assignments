package cn.dns.controller;

import cn.dns.service.inter.DnsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.annotation.Resource;


@Api(tags = "dnsService")
@Controller
public class DnsController {
    @Resource(name = "dnsService")
    private DnsService dnsService;

    @ApiOperation(value="getShortDns",notes = "getShortDnsNotes")
    @RequestMapping(path="/shortdns")
    @ResponseBody
    public String getShortDns(String shortUrl){
        return dnsService.getShortDns(shortUrl);
    }

    @ApiOperation(value="getLongDns",notes = "getLongDnsNotes")
    @RequestMapping(path="/longdns")
    @ResponseBody
    public String getLongDns(String longUrl){
        return dnsService.getLongDns(longUrl);
    }


}
