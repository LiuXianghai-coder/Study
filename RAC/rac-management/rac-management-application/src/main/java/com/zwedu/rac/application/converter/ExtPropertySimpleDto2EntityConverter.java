package com.zwedu.rac.application.converter;

import com.zwedu.rac.application.dto.ext.ExtPropertySimpleDto;
import com.zwedu.rac.domain.entity.ExtPropertyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 扩展属性dto-entity转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface ExtPropertySimpleDto2EntityConverter extends Dto2EntityConverter<ExtPropertySimpleDto, ExtPropertyEntity> {
    /**
     * 实例
     */
    ExtPropertySimpleDto2EntityConverter INSTANCE = Mappers.getMapper(ExtPropertySimpleDto2EntityConverter.class);
}