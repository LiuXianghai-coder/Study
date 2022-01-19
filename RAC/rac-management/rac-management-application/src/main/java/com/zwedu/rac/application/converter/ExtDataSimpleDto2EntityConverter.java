package com.zwedu.rac.application.converter;

import com.zwedu.rac.application.dto.ext.ExtDataSimpleDto;
import com.zwedu.rac.domain.entity.ExtDataEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 扩展属性值dto-entity转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface ExtDataSimpleDto2EntityConverter extends Dto2EntityConverter
        <ExtDataSimpleDto, ExtDataEntity> {
    /**
     * 实例
     */
    ExtDataSimpleDto2EntityConverter INSTANCE = Mappers.getMapper(ExtDataSimpleDto2EntityConverter.class);
}