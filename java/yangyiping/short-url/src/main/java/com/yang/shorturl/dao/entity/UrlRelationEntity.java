package com.yang.shorturl.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author yangyiping1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlRelationEntity implements Serializable {

    /**
     * 主键
     */
    private Long id;
    /**
     * 长地址
     */
    private String url;
    /**
     * 短地址
     */
    private String shortUrl;
}
