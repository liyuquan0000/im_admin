package com.jdd.imadmin.clinet;

import com.anoyi.grpc.constant.SerializeType;
import com.anoyi.grpc.service.GrpcRequest;
import com.anoyi.grpc.service.GrpcResponse;
import com.anoyi.grpc.util.ProtobufUtils;
import com.anoyi.rpc.CommonServiceGrpc;
import com.anoyi.rpc.GrpcService;
import com.google.protobuf.ByteString;
import com.jdd.imadmin.api.UserServiceByProtoStuff;
import com.jdd.imadmin.api.model.UserEntity;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class GrpcClient {

    private final ManagedChannel channel;
    private final CommonServiceGrpc.CommonServiceBlockingStub blockingStub;

    public GrpcClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext().build());
    }

    private GrpcClient(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = CommonServiceGrpc.newBlockingStub(channel);
    }

    public static void main(String[] args) throws Exception {
        GrpcClient client = new GrpcClient("localhost", 6666);
        try {
            for (int i = 0; i < 100; i++) {
                String words = "world - " + i;
                client.say(words);
            }
        } finally {
            client.shutdown();
        }
    }

    private void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    private void say(String words) {
        GrpcRequest grpcRequest = new GrpcRequest();
        grpcRequest.setClazz(UserServiceByProtoStuff.class.getName());
        grpcRequest.setMethod("findOne");
        Object[] params = {};
        grpcRequest.setArgs(params);
        System.out.println("远ReqT程调用 " + grpcRequest.getClazz() + "." + grpcRequest.getClazz() + " ");
        byte[] bytes = ProtobufUtils.serialize(grpcRequest);
        GrpcService.Request.Builder  builder=GrpcService.Request.newBuilder();
        builder.setSerialize(SerializeType.PROTOSTUFF.getValue());
        GrpcService.Request request = GrpcService.Request.newBuilder().
                setRequest(ByteString.copyFrom(bytes)).setSerialize(SerializeType.PROTOSTUFF.getValue()).build();
        GrpcService.Response response = blockingStub.handle(request);
        GrpcResponse response1=ProtobufUtils.deserialize(response.getResponse().toByteArray(), GrpcResponse.class);
        UserEntity userEntity= (UserEntity) response1.getResult();
        System.out.println("远程调用结果: " + userEntity);
    }

}