package com.jdd.imadmin.api.controller.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: liquan
 * @Date: 2020/4/23
 * @Version 1.0
 */
@Data
public class AutoReplyParam implements Serializable {


    /**
     * 关键词
     */
    private String keyword;




    /**
     *关联服务号id
     */
    private String service_open_id;

    /**
     *关联服务号id
     */
    private Integer content_type;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getService_open_id() {
        return service_open_id;
    }

    public void setService_open_id(String service_open_id) {
        this.service_open_id = service_open_id;
    }

    public Integer getContent_type() {
        return content_type;
    }

    public void setContent_type(Integer content_type) {
        this.content_type = content_type;
    }
}