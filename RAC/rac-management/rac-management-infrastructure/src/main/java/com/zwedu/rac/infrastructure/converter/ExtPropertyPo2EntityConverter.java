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
public interface ExtPropertyPo2EntityConverter extends Po2EntityConverter<ExtPropertyPo, ExtPropertyEntity> {
    /**
     * 实例
     */
    ExtPropertyPo2EntityConverter INSTANCE = Mappers.getMapper(ExtPropertyPo2EntityConverter.class);
}
