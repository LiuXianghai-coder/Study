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
public interface StrategySimpleDto2EntityConverter extends
        Dto2EntityConverter<StrategySimpleDto, StrategyEntity> {
    /**
     * 实例
     */
    StrategySimpleDto2EntityConverter INSTANCE = Mappers.getMapper(StrategySimpleDto2EntityConverter.class);
}
