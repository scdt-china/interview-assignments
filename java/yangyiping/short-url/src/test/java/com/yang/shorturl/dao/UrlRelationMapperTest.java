package com.yang.shorturl.dao;

import com.yang.shorturl.dao.entity.UrlRelationEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Objects;

@Slf4j
@SpringBootTest
public class UrlRelationMapperTest {

    private UrlRelationMapper urlRelationMapper;

    @Autowired
    public void setUrlRelationMapper(UrlRelationMapper urlRelationMapper) {
        this.urlRelationMapper = urlRelationMapper;
    }

    @Test
    void testSaveAndFind() {
        UrlRelationEntity entity = new UrlRelationEntity();
        int save = urlRelationMapper.save(entity);
        Assert.isTrue(save < 0, "期望-1的操作返回" + save);
        entity.setId(1L);
        entity.setUrl("http://www.baidu.com");
        entity.setShortUrl("http://a.cn/aaaaaaaa");
        save = urlRelationMapper.save(entity);
        Assert.isTrue(save > 0, "期望1的操作返回" + save);
        UrlRelationEntity relationEntity = urlRelationMapper.findById(1L);
        Assert.isTrue(Objects.nonNull(relationEntity), "没有查询到期望的结果");
        relationEntity = urlRelationMapper.findById(100L);
        Assert.isTrue(Objects.isNull(relationEntity), "有id期望为空的结果返回了值");
        relationEntity = urlRelationMapper.findById(null);
        Assert.isTrue(Objects.isNull(relationEntity), "无id期望为空的结果返回了值");
    }
}
