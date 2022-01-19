package com.zwedu.rac.application.converter;

import com.zwedu.rac.application.dto.func.FuncSimpleDto;
import com.zwedu.rac.application.dto.role.RoleSimpleDto;
import com.zwedu.rac.domain.entity.FuncEntity;
import com.zwedu.rac.domain.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 功能dto-entity转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface FuncSimpleDto2EntityConverter extends Dto2EntityConverter<FuncSimpleDto, FuncEntity> {
    /**
     * 实例
     */
    FuncSimpleDto2EntityConverter INSTANCE = Mappers.getMapper(FuncSimpleDto2EntityConverter.class);
}