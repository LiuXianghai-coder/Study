package com.zwedu.rac.application.converter;

import com.zwedu.rac.application.dto.ext.ExtPropertyComplexDto;
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
public interface ExtPropertyEntity2ComplexDtoConverter extends
        Entity2DtoConverter<ExtPropertyEntity, ExtPropertyComplexDto> {
    /**
     * 实例
     */
    ExtPropertyEntity2ComplexDtoConverter INSTANCE = Mappers.getMapper(ExtPropertyEntity2ComplexDtoConverter.class);
}
