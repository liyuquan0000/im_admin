package com.jdd.imadmin.dao.condition;

import lombok.Data;

import java.io.Serializable;

/**
 * @author @sailength on 2020/3/13.
 */
@Data
public class BatchUserOauthInsertVO implements Serializable {


    private static final long serialVersionUID = 6156703653941733112L;

    private String userId;

    private String openId;

    private String clientId;


}
