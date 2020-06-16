package com.jdd.imadmin.api;


import com.anoyi.grpc.annotation.GrpcService;
import com.anoyi.grpc.constant.SerializeType;
import com.jdd.imadmin.api.model.UserEntity;


@GrpcService(server = "user", serialization = SerializeType.PROTOSTUFF)
public interface UserServiceByProtoStuff {

    void insert(UserEntity userEntity);

    void deleteById(Long id);

    UserEntity findOne();
}
