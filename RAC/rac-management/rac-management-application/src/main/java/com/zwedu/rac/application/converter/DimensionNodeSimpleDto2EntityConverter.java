package com.zwedu.rac.application.converter;

import com.zwedu.rac.application.dto.dimension.DimensionNodeSimpleDto;
import com.zwedu.rac.domain.entity.DimensionNodeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 维度dto-entity转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface DimensionNodeSimpleDto2EntityConverter extends Dto2EntityConverter<DimensionNodeSimpleDto, DimensionNodeEntity> {
    /**
     * 实例
     */
    DimensionNodeSimpleDto2EntityConverter INSTANCE = Mappers.getMapper(DimensionNodeSimpleDto2EntityConverter.class);
}