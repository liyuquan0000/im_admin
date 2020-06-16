package com.jdd.imadmin.converter;

import com.jdd.imadmin.api.model.AutoReplyDTO;
import com.jdd.imadmin.common.util.DateConverter;
import com.jdd.imadmin.dao.entity.ImAutoReply;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

/**
 * @author @sailength on 2020/2/25.
 */

@Mapper(componentModel = "spring", uses = {DateConverter.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface AutoReplyConverter {


    AutoReplyDTO do2dto(ImAutoReply imAutoReply);

}
