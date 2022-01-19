package com.zwedu.rac.application.converter;

import com.zwedu.rac.application.dto.dimension.DimensionComplexDto;
import com.zwedu.rac.domain.entity.DimensionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 维度entity-dto转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface DimensionEntity2ComplexDtoConverter extends
        Entity2DtoConverter<DimensionEntity, DimensionComplexDto> {
    /**
     * 实例
     */
    DimensionEntity2ComplexDtoConverter INSTANCE = Mappers.getMapper(DimensionEntity2ComplexDtoConverter.class);
}
