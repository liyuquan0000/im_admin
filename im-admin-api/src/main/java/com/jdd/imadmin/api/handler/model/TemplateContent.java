package com.jdd.imadmin.api.handler.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author @sailength on 2020/3/26.
 */
@Data
public class TemplateContent implements Serializable {


    private static final long serialVersionUID = 8686704124837705112L;

    private String pic;
    @JSONField(name = "template_code")
    private String templateCode;
    @JSONField(name = "template_params")
    private Map<String, Object> temlateParams;
    @JSONField(name = "subject")
    private String subject;
}
