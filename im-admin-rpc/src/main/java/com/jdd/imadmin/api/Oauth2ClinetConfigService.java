package com.jdd.imadmin.api;

import com.anoyi.grpc.annotation.GrpcService;
import com.anoyi.grpc.constant.SerializeType;
import com.jdd.imadmin.api.model.BaseRpcDTO;
import com.jdd.imadmin.api.model.Oauth2ClientDTO;

/**
 * @author @sailength on 2020/3/6.
 */
@GrpcService(server = "oauth2config", serialization = SerializeType.PROTOSTUFF)
public interface Oauth2ClinetConfigService {

    BaseRpcDTO<Oauth2ClientDTO> getAuth2Config(String clientId);

    BaseRpcDTO<String> getOpenId(String clientId, String userId);
}
