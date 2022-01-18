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
public interface DimensionEntity2PoConverter extends Entity2PoConverter<DimensionEntity, DimensionPo> {
    /**
     * 实例
     */
    DimensionEntity2PoConverter INSTANCE = Mappers.getMapper(DimensionEntity2PoConverter.class);
}
