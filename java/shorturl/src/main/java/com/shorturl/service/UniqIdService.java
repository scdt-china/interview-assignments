
package com.shorturl.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 生成唯一自增ID服务（分布式系统考虑分布式自增ID）
 *
 * @author penghang
 * @created 7/8/21
 */
@Service
public class UniqIdService {

    private static final AtomicLong count = new AtomicLong(100000000);

    /**
     * 返回唯一自增ID
     *
     * @return
     */
    public Long getUniqId() {
        return count.incrementAndGet();
    }
}