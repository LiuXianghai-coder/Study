package com.zwedu.rac.application.converter;

import com.zwedu.rac.application.dto.dimension.DimensionNodeSimpleDto;
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
public interface DimensionNodeEntity2SimpleDtoConverter extends Entity2DtoConverter<DimensionNodeEntity, DimensionNodeSimpleDto> {
    /**
     * 实例
     */
    DimensionNodeEntity2SimpleDtoConverter INSTANCE = Mappers.getMapper(DimensionNodeEntity2SimpleDtoConverter.class);
}
