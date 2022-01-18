package com.zwedu.rac.application.converter;

import com.zwedu.rac.application.dto.role.RoleSimpleDto;
import com.zwedu.rac.domain.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 角色dto-entity转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface RoleSimpleDto2EntityConverter extends Dto2EntityConverter<RoleSimpleDto, RoleEntity> {
    /**
     * 实例
     */
    RoleSimpleDto2EntityConverter INSTANCE = Mappers.getMapper(RoleSimpleDto2EntityConverter.class);
}