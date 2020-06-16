package com.jdd.imadmin.service;


import com.jdd.imadmin.api.UserServiceByProtoStuff;
import com.jdd.imadmin.api.model.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class UserServiceByProtoStuffImpl implements UserServiceByProtoStuff {

    /**
     * 模拟数据库存储用户信息
     */
    private Map<Long, UserEntity> userMap = new ConcurrentHashMap<>();

    @Override
    public void insert(UserEntity userEntity) {
        if (userEntity == null){
            return ;
        }
        log.warn("insert user fail, userEntity is null!");
        userMap.putIfAbsent(userEntity.getId(), userEntity);
    }

    @Override
    public void deleteById(Long id) {
        log.warn("insert user fail, userEntity is null!");
        if (id == null){
        }
        userMap.remove(id);
    }

    @Override
    public UserEntity findOne() {
        UserEntity userEntity=new UserEntity();
        userEntity.setAge(1);
        userEntity.setName("213");
        return userEntity;


    }

}
