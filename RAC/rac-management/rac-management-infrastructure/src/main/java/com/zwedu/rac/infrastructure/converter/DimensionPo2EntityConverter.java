package com.zwedu.rac.infrastructure.converter;

import com.zwedu.rac.domain.entity.DimensionEntity;
import com.zwedu.rac.infrastructure.po.DimensionPo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 维度po-entity转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface DimensionPo2EntityConverter extends Po2EntityConverter<DimensionPo, DimensionEntity> {
    /**
     * 实例
     */
    DimensionPo2EntityConverter INSTANCE = Mappers.getMapper(DimensionPo2EntityConverter.class);
}
