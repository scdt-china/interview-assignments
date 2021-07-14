package com.fh.shorturl.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shorturl.model.UrlInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * URL映射表(UrlInfo)表数据库访问层
 */
@Mapper
public interface UrlInfoDao extends BaseMapper<UrlInfo> {
}