package com.jdd.imadmin.api.service.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author @sailength on 2020/4/14.
 */
@Data
public class CreateGroupDTO implements Serializable {

    private static final long serialVersionUID = -6869357355613318791L;

    @JSONField(name = "group_id")
    private String groupId;

}
