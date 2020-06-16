package com.jdd.imadmin.api.service.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author @sailength on 2020/2/20.
 */
@Data
public class ReplyInfoDto implements Serializable {

    private static final long serialVersionUID = 3328696479577541402L;


    @NotEmpty(message="content 不能为空!")
    private String content;

    @NotEmpty(message="keyword 不能为空!")
    private String keyword;
    @NotNull(message="contentType 不能为空!")
    private Integer contentType;
    @NotNull(message="replyId 不能为空!")
    private Long replyId;

}
