package com.zwedu.rac.infrastructure.converter;

import com.zwedu.rac.domain.entity.MenuEntity;
import com.zwedu.rac.infrastructure.po.MenuPo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 菜单po-entity转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface MenuEntity2PoConverter extends Entity2PoConverter<MenuEntity, MenuPo> {
    /**
     * 实例
     */
    MenuEntity2PoConverter INSTANCE = Mappers.getMapper(MenuEntity2PoConverter.class);
}
