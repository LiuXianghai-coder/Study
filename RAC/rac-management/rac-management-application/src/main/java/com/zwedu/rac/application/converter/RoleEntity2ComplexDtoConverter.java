package com.zwedu.rac.application.converter;

import com.zwedu.rac.application.dto.bizline.BizLineComplexDto;
import com.zwedu.rac.application.dto.role.RoleComplexDto;
import com.zwedu.rac.domain.entity.BizLineEntity;
import com.zwedu.rac.domain.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 角色entity-dto转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface RoleEntity2ComplexDtoConverter extends
        Entity2DtoConverter<RoleEntity, RoleComplexDto> {
    /**
     * 实例
     */
    RoleEntity2ComplexDtoConverter INSTANCE = Mappers.getMapper(RoleEntity2ComplexDtoConverter.class);
}
