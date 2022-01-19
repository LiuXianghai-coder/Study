package com.zwedu.rac.application.converter;

import com.zwedu.rac.application.dto.ext.ExtPropertySimpleDto;
import com.zwedu.rac.domain.entity.ExtPropertyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 扩展属性entity-dto转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface ExtPropertyEntity2SimpleDtoConverter extends
        Entity2DtoConverter<ExtPropertyEntity, ExtPropertySimpleDto> {
    /**
     * 实例
     */
    ExtPropertyEntity2SimpleDtoConverter INSTANCE = Mappers.getMapper(ExtPropertyEntity2SimpleDtoConverter.class);
}
