package com.sequoia.china.util;

import com.sequoia.china.common.SequoiaConfig;
import com.sequoia.china.container.DomainNameContainer;
import com.sequoia.china.exception.ErrorEnum;
import com.sequoia.china.exception.SequoiaRunTimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

/**
 * @Description Id工具类
 * @Author helichao
 * @Date 2021/6/24 9:03 下午
 */
@Component
public class IdUtil {

    private final static char[] chars= new char[]{'0','1','2','3','4','5','6','7','8','9',
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

    private SecureRandom random = new SecureRandom();

    @Autowired
    SequoiaConfig sequoiaConfig;

    @Autowired
    DomainNameContainer container;

    /**
     * 生成id
     *
     * @return
     */
    public String getId(){
        //第一次获取id，递归次数为0
        return getId(0);
    }

    /**
     * 生成id
     *
     * @param recursiveCount 当前递归次数
     * @return
     */
    private String getId(int recursiveCount){
        if (recursiveCount >= sequoiaConfig.getIdRecursiveMaxCount()){
            throw new SequoiaRunTimeException(ErrorEnum.SCE_0005);
        }
        StringBuffer idSb=new StringBuffer(8);
        for (int i = 0; i < 8; i++) {
            idSb.append(chars[random.nextInt(62)]);
        }
        String id = idSb.toString();
        //id重复，递归重新获取，经测试1000万次访问不存在id重复问题
        if (container.shortToLongMap.containsKey(id)){
            recursiveCount++;
            id=getId(recursiveCount);
        }
        return id;
    }

}
