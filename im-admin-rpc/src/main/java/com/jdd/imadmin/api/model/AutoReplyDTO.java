package com.jdd.imadmin.api.model;

import lombok.Data;

import java.util.Date;

/**
 * @author @sailength on 2020/4/29.
 */
@Data
public class AutoReplyDTO {

    private Long id;
    private Long serviceId;
    private String serviceOpenId;
    private String content;
    private String keyword;
    private Integer contentType;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private String createBy;
    private String updateBy;


}
