package com.jdd.imadmin.api.handler.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author @sailength on 2020/3/26.
 */
@Data
public class TemplateMessageVO implements Serializable {

    private static final long serialVersionUID = 3525887986188011302L;
    private String url;
    private String pic;
    private String subject;
    private String content;
    private Integer type;

    public TemplateMessageVO(String url, String pic, String subject, String content, Integer type) {
        this.url = url;
        this.pic = pic;
        this.subject = subject;
        this.content = content;
        this.type = type;
    }

    public TemplateMessageVO() {
    }
}
