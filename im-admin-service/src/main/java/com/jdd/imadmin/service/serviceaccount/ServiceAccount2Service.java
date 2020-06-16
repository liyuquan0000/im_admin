package com.jdd.imadmin.service.serviceaccount;

import com.jdd.imadmin.dao.entity.ImAutoReply;

/**
 * @author @sailength on 2020/2/20.
 */
public interface ServiceAccount2Service {

    ImAutoReply getAnyAutoReply(String serviceOpenId, String keyword);
}
