package com.sequoia.china.service.impl;

import cn.hutool.core.util.StrUtil;
import com.sequoia.china.container.DomainNameContainer;
import com.sequoia.china.exception.ErrorEnum;
import com.sequoia.china.exception.SequoiaRunTimeException;
import com.sequoia.china.service.IDomainNameConvertService;
import com.sequoia.china.util.IdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description 长短域名转换接口实现类
 * @Author helichao
 * @Date 2021/6/24 6:49 下午
 */
@Service
public class DomainNameConvertServiceImpl implements IDomainNameConvertService {

    private final static Logger log= LoggerFactory.getLogger(DomainNameConvertServiceImpl.class);

    @Autowired
    DomainNameContainer container;

    @Autowired
    IdUtil idUtil;

    /**
     * 长域名转短域名
     *
     * 备注：该代码场景设定为长短域名ip:port使用同一个
     *
     * @param longDomainName 长域名
     * @return 短域名
     */
    @Override
    public String longToShort(String longDomainName) {
        //1.判空
        if (StrUtil.isEmpty(longDomainName)){
            throw new SequoiaRunTimeException(ErrorEnum.SCE_0002);
        }
        //2.截取url，将http://ip:port/a/b/c截取prefixUrl=http://ip:port/和suffixUrl=a/b/c
        int index = getIndex(longDomainName);
        if (index<3){
            throw new SequoiaRunTimeException(ErrorEnum.SCE_0003,longDomainName);
        }
        String prefixUrl = longDomainName.substring(0,index+1);
        String suffixUrl = longDomainName.substring(index+1);
        if (StrUtil.isEmpty(suffixUrl)){
            throw new SequoiaRunTimeException(ErrorEnum.SCE_0003,longDomainName);
        }
        //3.从容器中获取短域名
        String shortDomainName = container.getShortDomainName(suffixUrl);
        if (StrUtil.isNotEmpty(shortDomainName)){
            return prefixUrl+shortDomainName;
        }
        //5.生成id；
        String id= idUtil.getId();
        container.put(suffixUrl,id);
        return prefixUrl+id;
    }

    /**
     * 短域名转长域名
     * @param shortDomainName 短域名
     * @return 长域名
     */
    @Override
    public String shortToLong(String shortDomainName) {
        //1.判空
        if (StrUtil.isEmpty(shortDomainName)){
            throw new SequoiaRunTimeException(ErrorEnum.SCE_0002);
        }
        //2.截取url，将http://ip:port/id截取prefixUrl=http://ip:port/和suffixUrl=id
        int index = getIndex(shortDomainName);
        if (index<3){
            throw new SequoiaRunTimeException(ErrorEnum.SCE_0003,shortDomainName);
        }
        String prefixUrl = shortDomainName.substring(0,index+1);
        String suffixUrl = shortDomainName.substring(index+1);
        if (StrUtil.isEmpty(suffixUrl)){
            throw new SequoiaRunTimeException(ErrorEnum.SCE_0003,shortDomainName);
        }
        //3.从容器中获取长域名
        String longDomainName = container.getLongDomainName(suffixUrl);
        return prefixUrl+longDomainName;
    }

    /**
     * 获取第三个/的位置
     * @param url 全url http://ip:port/a/b/c
     * @return 第三个/的位置
     */
    private int getIndex(String url){
        //找到第三个/，进行分割
        char[] chars = url.toCharArray();
        int num=0;
        //第三个/的位置
        int index=0;
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            //ASCII码中/=47
            if (ch!=47){
                continue;
            }
            num++;
            if (num==3){
                index=i;
            }
        }
        return index;
    }
}
