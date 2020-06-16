package com.jdd.imadmin.api.handler.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author @sailength on 2020/3/26.
 */
@Data
public class HrefMessageVO implements Serializable {

    private static final long serialVersionUID = -915696982625994212L;

    private String url;

    private String content;

    private Integer type;

    public HrefMessageVO(String url, String content, Integer type) {
        this.url = url;
        this.content = content;
        this.type = type;
    }
}
