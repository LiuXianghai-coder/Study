package com.zwedu.rac.application.converter;

import com.zwedu.rac.application.dto.dimension.DimensionSimpleDto;
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
public interface DimensionEntity2SimpleDtoConverter extends Entity2DtoConverter
        <DimensionEntity, DimensionSimpleDto> {
    /**
     * 实例
     */
    DimensionEntity2SimpleDtoConverter INSTANCE = Mappers.getMapper(DimensionEntity2SimpleDtoConverter.class);
}
