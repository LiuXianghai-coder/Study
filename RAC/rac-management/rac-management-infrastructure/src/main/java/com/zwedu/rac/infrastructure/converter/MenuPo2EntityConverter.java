package com.zwedu.rac.infrastructure.converter;

import com.zwedu.rac.domain.entity.MenuEntity;
import com.zwedu.rac.infrastructure.po.MenuPo;
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
public interface MenuPo2EntityConverter extends Po2EntityConverter<MenuPo, MenuEntity>{
    /**
     * 实例
     */
    MenuPo2EntityConverter INSTANCE = Mappers.getMapper(MenuPo2EntityConverter.class);


    /**
     * 转化为实体列表
     *
     * @param po               菜单Po列表
     * @param id2ChildCountMap 菜单ID对应的子节点数据集合
     * @return 菜单实体
     */
    @Mappings({
            @Mapping(target = "childCount", expression = "java(id2ChildCountMap.get(po.getId()))")
    })
    MenuEntity toEntity(MenuPo po, Map<Long, Long> id2ChildCountMap);

    /**
     * 转化为实体列表
     *
     * @param poList           菜单Po列表
     * @param id2ChildCountMap 菜单ID对应的子节点数据集合
     * @return 菜单实体
     */
    default List<MenuEntity> toEntityList(List<MenuPo> poList, Map<Long, Long> id2ChildCountMap) {
        return poList.stream().map(input -> toEntity(input, id2ChildCountMap)).collect(Collectors.toList());
    }
}
