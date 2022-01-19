package com.zwedu.rac.application.converter;

import com.zwedu.rac.application.dto.func.FuncSimpleDto;
import com.zwedu.rac.application.dto.role.RoleSimpleDto;
import com.zwedu.rac.domain.entity.FuncEntity;
import com.zwedu.rac.domain.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 功能entity-dto转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface FuncEntity2SimpleDtoConverter extends Entity2DtoConverter<FuncEntity, FuncSimpleDto> {
    /**
     * 实例
     */
    FuncEntity2SimpleDtoConverter INSTANCE = Mappers.getMapper(FuncEntity2SimpleDtoConverter.class);
}
