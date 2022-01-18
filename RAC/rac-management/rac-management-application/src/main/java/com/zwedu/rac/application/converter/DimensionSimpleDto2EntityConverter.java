package com.zwedu.rac.application.converter;

import com.zwedu.rac.application.dto.dimension.DimensionSimpleDto;
import com.zwedu.rac.domain.entity.DimensionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 维度dto-entity转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface DimensionSimpleDto2EntityConverter extends Dto2EntityConverter<DimensionSimpleDto, DimensionEntity> {
    /**
     * 实例
     */
    DimensionSimpleDto2EntityConverter INSTANCE = Mappers.getMapper(DimensionSimpleDto2EntityConverter.class);
}