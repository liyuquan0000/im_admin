package com.jdd.imadmin.api;

import com.anoyi.grpc.annotation.GrpcService;
import com.anoyi.grpc.constant.SerializeType;
import com.jdd.imadmin.api.model.AutoReplyDTO;
import com.jdd.imadmin.api.model.BaseRpcDTO;

/**
 * @author @sailength on 2020/4/29.
 */

@GrpcService(server = "autoReply", serialization = SerializeType.PROTOSTUFF)
public interface AutoReplyService {


    /** */
    public BaseRpcDTO<AutoReplyDTO> queryAutoReply(String serviceOpenId, String keyword);


}
