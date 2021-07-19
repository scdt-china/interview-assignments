package com.wanghui.utils;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

/**
 * @author wanghui
 * @title  ID生成器
 * @Date 2021-07-17 16:00
 * @Description
 */
public class IDMaker {
    private static final String ZK_ADDRESS = "127.0.0.1:2181";
    //Zk客户端
    CuratorFramework client = null;

    /**
     * 初始化客户端
     */
    public void init() {

        //创建客户端
        client = ZookeeperClientFactory.createSimple(ZK_ADDRESS);
        //启动客户端实例,连接服务器
        client.start();

    }

    /**
     * 客户端销毁
     */
    public void destroy() {
        if (null != client) {
            client.close();
        }
    }

    /**
     * 创建顺序节点
     * @param pathPefix
     * @return
     */
    private String createSeqNode(String pathPefix) {
        try {
            // 创建一个 ZNode 顺序节点
            String destPath = client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    //避免zookeeper的顺序节点暴增，需要删除创建的持久化顺序节点
                    //.withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(pathPefix);
            return destPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 生成id
     * @param nodeName
     * @return
     */
    public String makeId(String nodeName) {
        String str = createSeqNode(nodeName);
        if (null == str) {
            return null;
        }
        int index = str.lastIndexOf(nodeName);
        if (index >= 0) {
            index += nodeName.length();
            return index <= str.length() ? str.substring(index) : "";
        }
        return str;

    }
}