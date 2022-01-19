package com.zwedu.rac.infrastructure.converter;

import com.zwedu.rac.domain.entity.ExtDataEntity;
import com.zwedu.rac.infrastructure.po.ExtData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 扩展属性po-entity转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface ExtData2EntityConverter extends Po2EntityConverter<ExtData, ExtDataEntity> {
    /**
     * 实例
     */
    ExtData2EntityConverter INSTANCE = Mappers.getMapper(ExtData2EntityConverter.class);
}
