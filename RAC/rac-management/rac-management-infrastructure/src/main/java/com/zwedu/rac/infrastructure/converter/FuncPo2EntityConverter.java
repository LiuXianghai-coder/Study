package com.zwedu.rac.infrastructure.converter;

import com.zwedu.rac.domain.entity.FuncEntity;
import com.zwedu.rac.domain.entity.StrategyEntity;
import com.zwedu.rac.infrastructure.po.FuncExtPo;
import com.zwedu.rac.infrastructure.po.FuncPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 功能po-entity转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface FuncPo2EntityConverter extends Po2EntityConverter<FuncPo, FuncEntity> {
    /**
     * 实例
     */
    FuncPo2EntityConverter INSTANCE = Mappers.getMapper(FuncPo2EntityConverter.class);

    /**
     * 转化为实体列表
     *
     * @param po               功能Po列表
     * @param id2ChildCountMap 功能ID对应的子节点数据集合
     * @return 功能实体
     */
    @Mappings({
            @Mapping(target = "childCount", expression = "java(id2ChildCountMap.get(po.getId()))")
    })
    FuncEntity toEntity(FuncPo po, Map<Long, Long> id2ChildCountMap);

    /**
     * 转化为实体列表
     *
     * @param poList           功能Po列表
     * @param id2ChildCountMap 功能ID对应的子节点数据集合
     * @return 功能实体
     */
    default List<FuncEntity> toEntityList(List<FuncPo> poList, Map<Long, Long> id2ChildCountMap) {
        return poList.stream().map(input -> toEntity(input, id2ChildCountMap)).collect(Collectors.toList());
    }

    /**
     * 转化复合实体
     *
     * @param po                po对象
     * @param strategyEntityMap 策略map
     * @return 实体对象
     */
    @Mappings({
            @Mapping(target = "strategyEntity", expression = "java(strategyEntityMap.get(po.getStrategyId()))")
    })
    FuncEntity toComplexEntity(FuncExtPo po, Map<Long, StrategyEntity> strategyEntityMap);

    /**
     * 转化复合实体
     *
     * @param poList            功能PO列表
     * @param strategyEntityMap 策略map
     * @return 功能列表
     */
    default List<FuncEntity> toComplexEntityList(List<FuncExtPo> poList, Map<Long, StrategyEntity> strategyEntityMap) {
        return poList.stream().map(input -> toComplexEntity(input, strategyEntityMap)).collect(Collectors.toList());
    }
}
