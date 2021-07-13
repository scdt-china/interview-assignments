package com.tzg157.demo.service.number;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service(value = "simpleIdNumberService")
public class SimpleIdNumberServiceImpl implements IdNumberService{

    private volatile AtomicLong idNumber = new AtomicLong(1);

    @Override
    public Long getNextId() {
        return idNumber.getAndIncrement();
    }

    @Override
    public Long getCurrentId() {
        return idNumber.get();
    }
}
