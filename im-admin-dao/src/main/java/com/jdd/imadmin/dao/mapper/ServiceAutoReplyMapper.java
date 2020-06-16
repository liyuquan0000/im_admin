package com.jdd.imadmin.dao.mapper;

import com.jdd.imadmin.dao.base.BaseMapper;
import com.jdd.imadmin.dao.entity.ImAutoReply;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceAutoReplyMapper extends BaseMapper<ImAutoReply> {

    List<ImAutoReply> queryAutoReply(@Param("serviceId") String serviceId,@Param("keyword") String keyword,@Param("contentType") Integer contentType);

}