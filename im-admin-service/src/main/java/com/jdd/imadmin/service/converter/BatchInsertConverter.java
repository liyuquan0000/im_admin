package com.jdd.imadmin.service.converter;

import com.jdd.imadmin.common.util.DateConverter;
import com.jdd.imadmin.dao.condition.BatchUserOauthInsertVO;
import com.jdd.imadmin.service.model.BatchGenerateVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

/**
 * @author @sailength on 2020/3/13.
 */

@Mapper(componentModel = "spring", uses = {DateConverter.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface  BatchInsertConverter {

    @Mapping(target = "openId", expression = "java(java.util.UUID.randomUUID().toString().replaceAll(\"-\", \"\"))")
    BatchUserOauthInsertVO  vo2do(BatchGenerateVO batchGenerateVO);


    List<BatchUserOauthInsertVO> vos2dos(List<BatchGenerateVO> list);
}
