package com.zwedu.rac.application.converter;

import com.zwedu.rac.application.dto.strategy.StrategySimpleDto;
import com.zwedu.rac.domain.entity.StrategyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 访问策略实体entity-dto转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface StrategyEntity2SimpleDtoConverter extends
        Entity2DtoConverter<StrategyEntity, StrategySimpleDto> {
    /**
     * 实例
     */
    StrategyEntity2SimpleDtoConverter INSTANCE = Mappers.getMapper(StrategyEntity2SimpleDtoConverter.class);
}
