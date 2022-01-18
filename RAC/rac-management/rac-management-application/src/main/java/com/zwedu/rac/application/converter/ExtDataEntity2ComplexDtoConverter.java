package com.zwedu.rac.application.converter;

import com.google.common.collect.Lists;
import com.zwedu.rac.application.dto.ext.ExtDataComplexDto;
import com.zwedu.rac.domain.common.enums.ExtPropertyTypeEnum;
import com.zwedu.rac.domain.entity.DictionaryNodeEntity;
import com.zwedu.rac.domain.entity.ExtDataEntity;
import com.zwedu.rac.domain.entity.ExtPropertyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 扩展属性值entity-dto转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface ExtDataEntity2ComplexDtoConverter extends
        Entity2DtoConverter<ExtDataEntity, ExtDataComplexDto> {
    /**
     * 实例
     */
    ExtDataEntity2ComplexDtoConverter INSTANCE = Mappers.getMapper(ExtDataEntity2ComplexDtoConverter.class);

    /**
     * 转化为实体列表
     *
     * @param entity            字典节点实体对象
     * @param dictionaryNodeMap 字典节点ID对应的子节点数据集合
     * @return 字典节点实体
     */
    @Mappings({
            @Mapping(target = "text", expression = "java(getText(entity,extPropertyMap,dictionaryNodeMap))"),
            @Mapping(target = "extPropertyName",
                    expression = "java(extPropertyMap.get(entity.getExtPropertyId()).getCnName())")
    })
    ExtDataComplexDto toEntity(ExtDataEntity entity, Map<Long, ExtPropertyEntity> extPropertyMap,
                               Map<Long, Map<Integer, DictionaryNodeEntity>> dictionaryNodeMap);

    /**
     * 获取扩展属性文本
     *
     * @param entity            实体
     * @param extPropertyMap    扩展属性map
     * @param dictionaryNodeMap 字典节点map
     * @return 文本
     */
    default String getText(ExtDataEntity entity, Map<Long, ExtPropertyEntity> extPropertyMap,
                           Map<Long, Map<Integer, DictionaryNodeEntity>> dictionaryNodeMap) {
        return extPropertyMap.get(entity.getExtPropertyId()).getType().equals(ExtPropertyTypeEnum.STRING.getValue()) ?
                entity.getValue() : dictionaryNodeMap.get(extPropertyMap.get(entity.getExtPropertyId())
                .getDictionaryId()).get(Integer.parseInt(entity.getValue())).getCnName();

    }

    /**
     * entityList 转 dtoList
     *
     * @param recordList        实体列表对象
     * @param dictionaryNodeMap 字典节点ID对应的子节点数据集合
     * @return dto对象
     */
    default List<ExtDataComplexDto> toDtoList(List<ExtDataEntity> recordList,
                                              Map<Long, ExtPropertyEntity> extPropertyMap,
                                              Map<Long, Map<Integer, DictionaryNodeEntity>> dictionaryNodeMap) {
        if (CollectionUtils.isEmpty(recordList)) {
            return Lists.newArrayList();
        }
        return recordList.stream().map(input -> toEntity(input, extPropertyMap, dictionaryNodeMap))
                .collect(Collectors.toList());
    }


}
