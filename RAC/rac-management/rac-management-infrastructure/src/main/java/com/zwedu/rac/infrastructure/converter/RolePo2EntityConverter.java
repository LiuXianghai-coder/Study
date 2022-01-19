package com.zwedu.rac.infrastructure.converter;

import com.zwedu.rac.domain.entity.RoleEntity;
import com.zwedu.rac.domain.entity.UserEntity;
import com.zwedu.rac.infrastructure.po.RolePo;
import com.zwedu.rac.infrastructure.po.UserPo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 角色po-entity转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface RolePo2EntityConverter extends Po2EntityConverter<RolePo, RoleEntity> {
    /**
     * 实例
     */
    RolePo2EntityConverter INSTANCE = Mappers.getMapper(RolePo2EntityConverter.class);
}
