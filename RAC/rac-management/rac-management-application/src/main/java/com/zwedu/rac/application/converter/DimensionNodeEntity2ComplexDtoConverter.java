package com.zwedu.rac.application.converter;

import com.zwedu.rac.application.dto.dimension.DimensionNodeComplexDto;
import com.zwedu.rac.domain.entity.DimensionNodeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 维度entity-dto转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface DimensionNodeEntity2ComplexDtoConverter extends
        Entity2DtoConverter<DimensionNodeEntity, DimensionNodeComplexDto> {
    /**
     * 实例
     */
    DimensionNodeEntity2ComplexDtoConverter INSTANCE = Mappers.getMapper(DimensionNodeEntity2ComplexDtoConverter.class);
}
