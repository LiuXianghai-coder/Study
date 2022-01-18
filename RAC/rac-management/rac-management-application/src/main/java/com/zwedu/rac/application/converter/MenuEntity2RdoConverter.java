package com.zwedu.rac.application.converter;

import com.zwedu.rac.domain.entity.MenuEntity;
import com.zwedu.rac.sdk.rdo.MenuRdo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 菜单entity-rdo转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface MenuEntity2RdoConverter extends
        Entity2RdoConverter<MenuEntity, MenuRdo> {
    /**
     * 实例
     */
    MenuEntity2RdoConverter INSTANCE = Mappers.getMapper(MenuEntity2RdoConverter.class);
}
