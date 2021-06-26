package com.sequoia.china.container;

import cn.hutool.core.util.StrUtil;
import com.sequoia.china.common.SequoiaConfig;
import com.sequoia.china.exception.ErrorEnum;
import com.sequoia.china.exception.SequoiaRunTimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 域名容器
 * @Author helichao
 * @Date 2021/6/24 7:38 下午
 */
@Component
public class DomainNameContainer {

    @Autowired
    SequoiaConfig sequoiaConfig;

    /**
     * 存储key=长域名，value=短域名
     */
    public ConcurrentHashMap<String,String> longToShortMap=new ConcurrentHashMap();

    /**
     * 存储key=短域名，value=长域名
     */
    public ConcurrentHashMap<String,String> shortToLongMap=new ConcurrentHashMap();

    /**
     * 用于lru算法，String格式为：longDomainName+"|"+shortDomainName
     */
    public LinkedList<String> linkedList=new LinkedList<>();

    /**
     * 保存长短域名映射关系
     *
     * @param longDomainName 长域名
     * @param shortDomainName 短域名
     */
    public synchronized void put(String longDomainName,String shortDomainName){
        if (StrUtil.isEmpty(longDomainName)||StrUtil.isEmpty(shortDomainName)){
            return;
        }
        //1.判断是否超过容器上限，如果大于或等于maxCount，则移除linkedlist first元素
        if (linkedList.size()>= sequoiaConfig.getContainerMaxCount()) {
            //找到list中第一个元素，并删除
            String first = linkedList.getFirst();
            linkedList.removeFirst();
            //删除map中对应的元素
            String[] split = first.split("\\|");
            String longD=split[0];
            String shortD=split[1];
            longToShortMap.remove(longD);
            shortToLongMap.remove(shortD);
        }
        //2.将值放入map，建立映射关系
        longToShortMap.put(longDomainName,shortDomainName);
        shortToLongMap.put(shortDomainName,longDomainName);
        //3.保存至linkedlist last位置，
        linkedList.addLast(longDomainName+"|"+shortDomainName);
    }

    /**
     * 根据短域名获取长域名
     * @param shortDomainName 短域名
     * @return 长域名
     */
    public synchronized String getLongDomainName(String shortDomainName){
        if (StrUtil.isEmpty(shortDomainName)){
            return null;
        }
        //1.根据short获取long
        String longDomainName = shortToLongMap.get(shortDomainName);
        if (StrUtil.isEmpty(longDomainName)) {
            throw new SequoiaRunTimeException(ErrorEnum.SCE_0004);
        }
        //2.更新在linkedlist中的位置
        refreshLocation(longDomainName,shortDomainName);
        return longDomainName;
    }

    /**
     * 根据长域名获取短域名
     * @param longDomainName 长域名
     * @return 短域名
     */
    public synchronized String getShortDomainName(String longDomainName){
        if (StrUtil.isEmpty(longDomainName)){
            return null;
        }
        //1.根据long获取short
        String shortDomainName = longToShortMap.get(longDomainName);
        if (StrUtil.isEmpty(shortDomainName)) {
            return null;
        }
        //2.更新在linkedlist中的位置
        refreshLocation(longDomainName,shortDomainName);
        return shortDomainName;
    }

    /**
     * 刷新key的位置，将最近访问的key移动至在last位置
     * @param longDomainName 长域名
     * @param shortDomainName 短域名
     */
    private void refreshLocation(String longDomainName,String shortDomainName){
        String linkedlistNode = longDomainName + "|" + shortDomainName;
        linkedList.remove(linkedlistNode);
        linkedList.addLast(linkedlistNode);
    }


}
