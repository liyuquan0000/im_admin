package com.jdd.imadmin.api.service.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author @sailength on 2020/2/20.
 */
@Data
public class AddAutoReplyDto implements Serializable {

    private static final long serialVersionUID = -5879916000374305203L;
    @NotEmpty(message = "token 不能为空!")
    @JSONField(name = "token")
    private String token;
    @NotEmpty(message = "serviceOpenId 不能为空!")
    @JSONField(name = "service_open_id")
    private String serviceOpenId;
    @NotEmpty(message = "content 不能为空!")
    @JSONField(name = "content")
    private String content;
    @NotEmpty(message = "keyword 不能为空!")
    @JSONField(name = "keyword")
    private String keyword;
    @NotNull(message = "contentType 不能为空!")
    @JSONField(name = "content_type")
    private Integer contentType;

}
