package com.jdd.imadmin.api.service.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author @sailength on 2020/2/20.
 */
@Data
public class UserInfoDto implements Serializable {

    private static final long serialVersionUID = 2545546597329701439L;

    @JSONField(name = "open_id")
    private String openId;
    @JSONField(name = "nick_name")
    private String nickName;
    @JSONField(name = "head_pic")
    private String headPic;
}
