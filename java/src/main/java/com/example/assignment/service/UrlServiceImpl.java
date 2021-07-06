package com.example.assignment.service;

import com.example.assignment.dao.UrlRecordMapper;
import com.example.assignment.dto.UrlRecord;
import com.example.assignment.utils.HexUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UrlServiceImpl implements UrlService{

    @Autowired
    private UrlRecordMapper urlRecordMapper;

    @Override
    public String convertToShort(String longUrl) {

        UrlRecord record=new UrlRecord();

        record.setLongUrl(longUrl);
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        urlRecordMapper.insertSelective(record);
        long primaryKey = record.getId();
        return HexUtil._10_to_62(primaryKey);
    }

    @Override
    public String convertToLong(String shortUrl) {
        Long primaryKey=HexUtil._62_to_10(shortUrl);
        UrlRecord record = urlRecordMapper.selectByPrimaryKey(primaryKey);
        if(record!=null){
            return record.getLongUrl();
        }
        throw new IllegalArgumentException("短链接不存在！");
    }
}
