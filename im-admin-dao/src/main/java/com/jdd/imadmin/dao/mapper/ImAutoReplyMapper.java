package com.jdd.imadmin.dao.mapper;

import com.jdd.imadmin.dao.base.BaseMapper;
import com.jdd.imadmin.dao.entity.ImAutoReply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ImAutoReplyMapper extends BaseMapper<ImAutoReply> {


    List<ImAutoReply> queryAutoReplyBySeriviceId(@Param("serviceId") String serviceId);

    void deleteAutoReplyByServiceId(@Param("serviceId") String serviceId, @Param("replyId")Long replyId);

    List<ImAutoReply> queryAutoReplyByKeyWord(@Param("keyWord") String keyWord, @Param("serviceId") String serviceId);

}