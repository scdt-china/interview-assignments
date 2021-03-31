package com.d00216118.demo.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Yu Chen
 * @email D00216118@student.dkit.ie
 * @date 12:24 下午 2021/3/29
 **/
@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
@Data
public class InfoUrl implements Serializable {

    @Id
    @GeneratedValue(strategy =GenerationType.SEQUENCE)
    private Long id;

    private String url;

    private String tinyUrl;

    private long userId;

    @CreatedDate
    private Timestamp createdDate;



}
