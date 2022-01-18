package com.zwedu.rac.application.converter;

import com.zwedu.rac.domain.entity.UserEntity;
import com.zwedu.rac.application.dto.user.UserComplexDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 用户entity-dto转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface UserEntity2ComplexDtoConverter extends
        Entity2DtoConverter<UserEntity, UserComplexDto> {
    /**
     * 实例
     */
    UserEntity2ComplexDtoConverter INSTANCE = Mappers.getMapper(UserEntity2ComplexDtoConverter.class);
}
