package com.zwedu.rac.infrastructure.converter;

import com.zwedu.rac.domain.entity.ExtDataEntity;
import com.zwedu.rac.infrastructure.po.ExtDataPo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 扩展属性po-entity转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface ExtDataPo2EntityConverter extends Po2EntityConverter<ExtDataPo, ExtDataEntity> {
    /**
     * 实例
     */
    ExtDataPo2EntityConverter INSTANCE = Mappers.getMapper(ExtDataPo2EntityConverter.class);
}
