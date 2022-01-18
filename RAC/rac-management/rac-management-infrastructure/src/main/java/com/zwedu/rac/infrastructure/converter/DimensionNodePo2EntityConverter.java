package com.zwedu.rac.infrastructure.converter;

import com.zwedu.rac.domain.entity.DimensionNodeEntity;
import com.zwedu.rac.infrastructure.po.DimensionNodePo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 维度节点po-entity转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface DimensionNodePo2EntityConverter extends Po2EntityConverter<DimensionNodePo, DimensionNodeEntity> {
    /**
     * 实例
     */
    DimensionNodePo2EntityConverter INSTANCE = Mappers.getMapper(DimensionNodePo2EntityConverter.class);


    /**
     * 转化为实体列表
     *
     * @param po               维度节点节点Po列表
     * @param id2ChildCountMap 维度节点节点ID对应的子节点数据集合
     * @return 维度节点节点实体
     */
    @Mappings({
            @Mapping(target = "childCount", expression = "java(id2ChildCountMap.get(po.getId()))")
    })
    DimensionNodeEntity toEntity(DimensionNodePo po, Map<Long, Long> id2ChildCountMap);

    /**
     * 转化为实体列表
     *
     * @param poList           维度节点节点Po列表
     * @param id2ChildCountMap 维度节点节点ID对应的子节点数据集合
     * @return 维度节点节点实体
     */
    default List<DimensionNodeEntity> toEntityList(List<DimensionNodePo> poList, Map<Long, Long> id2ChildCountMap) {
        return poList.stream().map(input -> toEntity(input, id2ChildCountMap)).collect(Collectors.toList());
    }
}
