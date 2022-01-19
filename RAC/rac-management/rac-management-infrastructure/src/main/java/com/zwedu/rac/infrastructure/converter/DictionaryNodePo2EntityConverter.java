package com.zwedu.rac.infrastructure.converter;

import com.zwedu.rac.domain.entity.DictionaryNodeEntity;
import com.zwedu.rac.infrastructure.po.DictionaryNodePo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 字典节点po-entity转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface DictionaryNodePo2EntityConverter extends Po2EntityConverter<DictionaryNodePo, DictionaryNodeEntity> {
    /**
     * 实例
     */
    DictionaryNodePo2EntityConverter INSTANCE = Mappers.getMapper(DictionaryNodePo2EntityConverter.class);


    /**
     * 转化为实体列表
     *
     * @param po               字典节点Po列表
     * @param id2ChildCountMap 字典节点ID对应的子节点数据集合
     * @return 字典节点实体
     */
    @Mappings({
            @Mapping(target = "childCount", expression = "java(id2ChildCountMap.get(po.getId()))")
    })
    DictionaryNodeEntity toEntity(DictionaryNodePo po, Map<Long, Long> id2ChildCountMap);

    /**
     * 转化为实体列表
     *
     * @param poList           字典节点Po列表
     * @param id2ChildCountMap 字典节点ID对应的子节点数据集合
     * @return 字典节点实体
     */
    default List<DictionaryNodeEntity> toEntityList(List<DictionaryNodePo> poList, Map<Long, Long> id2ChildCountMap) {
        return poList.stream().map(input -> toEntity(input, id2ChildCountMap)).collect(Collectors.toList());
    }
}
