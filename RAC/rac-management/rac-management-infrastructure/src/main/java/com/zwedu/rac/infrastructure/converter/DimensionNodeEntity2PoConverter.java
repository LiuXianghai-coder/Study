package com.zwedu.rac.infrastructure.converter;

import com.zwedu.rac.domain.entity.DimensionNodeEntity;
import com.zwedu.rac.infrastructure.po.DimensionNodePo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 维度节点po-entity转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface DimensionNodeEntity2PoConverter extends Entity2PoConverter<DimensionNodeEntity, DimensionNodePo> {
    /**
     * 实例
     */
    DimensionNodeEntity2PoConverter INSTANCE = Mappers.getMapper(DimensionNodeEntity2PoConverter.class);
}
