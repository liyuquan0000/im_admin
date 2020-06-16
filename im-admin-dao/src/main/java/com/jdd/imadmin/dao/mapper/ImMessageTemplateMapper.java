package com.jdd.imadmin.dao.mapper;

import com.jdd.imadmin.dao.base.BaseMapper;
import com.jdd.imadmin.dao.entity.ImMessageTemplate;
import org.springframework.stereotype.Repository;

@Repository
public interface ImMessageTemplateMapper extends BaseMapper<ImMessageTemplate> {

    String selectMaxCode();
}