package com.zwedu.rac.application.converter;

import com.zwedu.rac.application.dto.menu.MenuSimpleDto;
import com.zwedu.rac.domain.entity.MenuEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 菜单entity-dto转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface MenuEntity2SimpleDtoConverter extends Entity2DtoConverter
        <MenuEntity, MenuSimpleDto> {
    /**
     * 实例
     */
    MenuEntity2SimpleDtoConverter INSTANCE = Mappers.getMapper(MenuEntity2SimpleDtoConverter.class);
}
