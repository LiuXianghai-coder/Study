package com.zwedu.rac.infrastructure.converter;

import com.zwedu.rac.domain.entity.UserEntity;
import com.zwedu.rac.infrastructure.po.UserPo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 用户po-entity转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface UserEntity2PoConverter extends Entity2PoConverter<UserEntity, UserPo> {
    /**
     * 实例
     */
    UserEntity2PoConverter INSTANCE = Mappers.getMapper(UserEntity2PoConverter.class);
}
