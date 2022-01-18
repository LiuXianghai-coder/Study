package com.zwedu.rac.infrastructure.converter;

import com.zwedu.rac.domain.entity.ExtPropertyEntity;
import com.zwedu.rac.infrastructure.po.ExtPropertyPo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 扩展属性po-entity转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface ExtPropertyEntity2PoConverter extends Entity2PoConverter<ExtPropertyEntity, ExtPropertyPo> {
    /**
     * 实例
     */
    ExtPropertyEntity2PoConverter INSTANCE = Mappers.getMapper(ExtPropertyEntity2PoConverter.class);
}
