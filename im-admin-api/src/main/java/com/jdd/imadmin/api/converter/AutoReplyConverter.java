package com.jdd.imadmin.api.converter;

import com.jdd.imadmin.api.service.dto.ReplyInfoDto;
import com.jdd.imadmin.common.util.DateConverter;
import com.jdd.imadmin.dao.entity.ImAutoReply;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

/**
 * @author @sailength on 2020/2/25.
 */

@Mapper(componentModel = "spring", uses = {DateConverter.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface AutoReplyConverter {

    ImAutoReply dto2do(ReplyInfoDto param);

    List<ImAutoReply> dtos2dos(List<ReplyInfoDto> list);

    @Mapping(target = "content", source = "content")
    @Mapping(target = "keyword", source = "keyword")
    @Mapping(target = "contentType", source = "contentType")
    @Mapping(target = "replyId", source = "id")
    ReplyInfoDto do2dto(ImAutoReply imAutoReply);

    List<ReplyInfoDto> dos2dtos(List<ImAutoReply> list);

}
