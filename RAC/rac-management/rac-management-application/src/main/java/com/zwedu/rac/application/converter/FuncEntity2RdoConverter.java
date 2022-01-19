package com.zwedu.rac.application.converter;

import com.zwedu.rac.domain.entity.FuncEntity;
import com.zwedu.rac.domain.entity.StrategyEntity;
import com.zwedu.rac.sdk.rdo.FuncRdo;
import com.zwedu.rac.sdk.rdo.StrategyRdo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 功能entity-dto转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface FuncEntity2RdoConverter extends Entity2RdoConverter<FuncEntity, FuncRdo> {
    /**
     * 实例
     */
    FuncEntity2RdoConverter INSTANCE = Mappers.getMapper(FuncEntity2RdoConverter.class);

    /**
     * 策略转化
     *
     * @param entity 策略实体
     * @return 策略对象
     */
    StrategyRdo toStrategyRdo(StrategyEntity entity);

    /**
     * 实体转化方法
     *
     * @param entity 实体
     * @return rdo对象
     */
    @Mappings({
            @Mapping(target = "urlSet", expression = "java(entity.getUrls())"),
            @Mapping(target = "strategyRdo", expression = "java(toStrategyRdo(entity.getStrategyEntity()))")
    })
    FuncRdo toRdo(FuncEntity entity);

    /**
     * 实体列表数据转换
     *
     * @param entityList 实体列表
     * @return rdo列表
     */
    List<FuncRdo> toRdoList(List<FuncEntity> entityList);
}
