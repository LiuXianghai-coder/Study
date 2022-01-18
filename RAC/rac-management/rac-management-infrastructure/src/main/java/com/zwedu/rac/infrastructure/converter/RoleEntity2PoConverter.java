package com.zwedu.rac.infrastructure.converter;

import com.zwedu.rac.domain.entity.RoleEntity;
import com.zwedu.rac.infrastructure.po.RolePo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 角色po-entity转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface RoleEntity2PoConverter extends Entity2PoConverter<RoleEntity, RolePo> {
    /**
     * 实例
     */
    RoleEntity2PoConverter INSTANCE = Mappers.getMapper(RoleEntity2PoConverter.class);
}
