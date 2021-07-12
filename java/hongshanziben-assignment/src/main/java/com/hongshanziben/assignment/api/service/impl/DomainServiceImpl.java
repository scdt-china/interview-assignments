package com.hongshanziben.assignment.api.service.impl;

import com.hongshanziben.assignment.api.service.DomainService;
import com.hongshanziben.assignment.constant.Container;
import com.hongshanziben.assignment.utils.DomainUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 域名转换接口实现
 *
 * @author KINGSJ.YUAN@FOXMAIL.COM
 * @date 2021-07-08
 */
@Service
public class DomainServiceImpl implements DomainService {

    @Override
    public String createShortDomain(String url) {
        String shortDomain = DomainUtils.getShortDomain();
        return this.checkShortDomain(shortDomain, url);
    }

    @Override
    public String getDomain(String url) {
        return Container.container.get(url);
    }

    protected synchronized String checkShortDomain(String shortDomain, String url) {
        //有长链接，直接返回短连接code
        String sDomain = Container.checkContainer.get(url);
        if (StringUtils.isNotEmpty(sDomain)) {
            return sDomain;
        } else {
            //避免短链code发生碰撞，发生后重试
            String longDomain = Container.container.get(shortDomain);
            if (StringUtils.isEmpty(longDomain)) {
                Container.container.put(shortDomain, url);
                Container.checkContainer.put(url, shortDomain);
            } else {
                checkShortDomain(DomainUtils.getShortDomain(), url);
            }
        }


        return shortDomain;
    }

}
