package com.yang.shorturl.dao;

import com.yang.shorturl.dao.entity.UrlRelationEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static com.yang.shorturl.Constant.LONG_ONE;

/**
 * 假设有一个持久化的存储
 *
 * @author yangyiping1
 */
@Slf4j
@Component
@ConfigurationProperties(prefix = "short.url.dao.urlrelation")
public class UrlRelationMapper {

    private Map<Long, UrlRelationEntity> data;

    private Integer size;
    /**
     * 记录清理到的记录位置
     */
    private AtomicLong offset;

    public void setSize(Integer size) {
        this.size = size;
    }

    @PostConstruct
    private void init() {
        data = new ConcurrentHashMap<>();
        offset = new AtomicLong();
    }

    /**
     * 假装持久化
     *
     * @param entity
     * @return
     */
    public int save(UrlRelationEntity entity) {
        Long id = entity.getId();
        if (Objects.nonNull(id)) {
            data.put(id, entity);
            clearOldestData();
            return 1;
        }
        return -1;
    }

    /**
     * 尝试清除超出限制的数据,防止内存溢出
     */
    private void clearOldestData() {
        if (data.size() > size) {
            for (; ; ) {
                long offsetValue = offset.getAndAdd(LONG_ONE);
                if (Objects.nonNull(data.remove(offsetValue))) {
                    log.info("id为{}对应的记录已清除", offsetValue);
                    break;
                }
            }
        }
    }

    /**
     * 根据主键查询记录
     *
     * @param id
     * @return
     */
    public UrlRelationEntity findById(Long id) {
        if (Objects.nonNull(id)) {
            return data.get(id);
        }
        return null;
    }

}
