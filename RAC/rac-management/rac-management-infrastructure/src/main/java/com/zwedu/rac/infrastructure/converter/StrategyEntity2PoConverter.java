package com.zwedu.rac.infrastructure.converter;

import com.zwedu.rac.domain.entity.StrategyEntity;
import com.zwedu.rac.infrastructure.po.StrategyPo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 访问策略po-entity转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface StrategyEntity2PoConverter extends Entity2PoConverter<StrategyEntity, StrategyPo> {
    /**
     * 实例
     */
    StrategyEntity2PoConverter INSTANCE = Mappers.getMapper(StrategyEntity2PoConverter.class);
}
