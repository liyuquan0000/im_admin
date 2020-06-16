package com.jdd.imadmin.api.converter;

import com.jdd.im.sdk.model.QueryWatchPageResult;
import com.jdd.imadmin.api.service.dto.UserInfoDto;
import com.jdd.imadmin.common.util.DateConverter;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

/**
 * @author @sailength on 2020/2/20.
 *         转化用户信息mapper
 */
@Mapper(componentModel = "spring", uses = {DateConverter.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface UserInfoConverter {

    UserInfoDto vo2dto(QueryWatchPageResult param);

    List<UserInfoDto> vos2dtos(List<QueryWatchPageResult> param);


}
