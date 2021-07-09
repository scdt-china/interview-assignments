package com.wangxb.convert.mem;

import com.google.common.collect.Maps;

import java.util.Map;

public class CacheMap {
    private Map<String,Node> map = Maps.newHashMap();
    private Node firstNode = null;
    private Node lastNode = null;
    int size = 0;

    MemeryManger memeryManger = new MemeryManger(this);

    public CacheMap(){
        memeryManger.start();
    }

    public synchronized String get(String name){
        String ret = null;
        Node node = getNode(name);
        if (node != null){
            ret = node.getValue();
        }
        return ret;
    }

    private synchronized Node getNode(String name){
        Node findNode = map.get(name);
        if (findNode == null){
            return findNode;
        }
        updateNode(findNode);
        return findNode;
    }

    public synchronized Node put(String name,String value){
        Node node = new Node(name,value);
        Node findNode = map.get(name);
        if (findNode == null){
            addNode(node);
        }else{
            removeNode(findNode);
            findNode = null;
            addNode(node);
        }
        map.put(name,node);
        return  node;
    }

    private synchronized void  addNode(Node node){
        if (firstNode == null){
            firstNode = node;
        }else {
            if (lastNode == null){
                lastNode = node;
                lastNode.setPre(firstNode);
                firstNode.setNext(lastNode);
            }else {
                node.setPre(lastNode);
                lastNode.setNext(node);
                lastNode = node;
            }
        }
        size++;
    }

    private synchronized void removeNode(Node node){
        String name = node.getName();
        if (node == firstNode){
            if (firstNode.getNext() == null){
                firstNode = null;
            }else{
                firstNode = firstNode.getNext();
            }
        }else if (node == lastNode){
            lastNode = lastNode.getPre();
            if (lastNode != null) {
                lastNode.setNext(null);
            }
        }else{
            Node preNode = node.getPre();
            Node nextNode = node.getNext();

            preNode.setNext(nextNode);
            nextNode.setPre(preNode);
        }
        map.remove(name);
        size--;
    }

    private synchronized void updateNode(Node node){
        if (lastNode == node){
            Node preNode = lastNode.getPre();
            preNode.setNext(null);
            lastNode = preNode;
            node.setPre(null);
            node.setNext(firstNode);
            firstNode = node;
        }else if (firstNode == node){
            return;
        }else{
            Node preNode = node.getPre();
            Node nextNode = node.getNext();

            preNode.setNext(nextNode);
            nextNode.setPre(preNode);
            node.setPre(null);
            node.setNext(firstNode);
            firstNode.setPre(node);
            firstNode = node;
        }
    }

    public synchronized void clear(int num){
        for(int i = 0;i<num;i++){
            Node node = lastNode;
            if (node == null){
                break;
            }
            removeNode(node);
            node = null;
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
