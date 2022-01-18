package com.zwedu.rac.application.converter;

import com.zwedu.rac.application.dto.strategy.StrategyComplexDto;
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
public interface StrategyEntity2ComplexDtoConverter extends
        Entity2DtoConverter<StrategyEntity, StrategyComplexDto> {
    /**
     * 实例
     */
    StrategyEntity2ComplexDtoConverter INSTANCE = Mappers.getMapper(StrategyEntity2ComplexDtoConverter.class);
}
