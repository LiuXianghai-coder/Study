package com.zwedu.rac.application.converter;

import com.zwedu.rac.application.dto.ext.ExtDataSimpleDto;
import com.zwedu.rac.domain.entity.ExtDataEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 扩展属性entity-dto转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface ExtDataEntity2SimpleDtoConverter extends
        Entity2DtoConverter<ExtDataEntity, ExtDataSimpleDto> {
    /**
     * 实例
     */
    ExtDataEntity2SimpleDtoConverter INSTANCE = Mappers.getMapper(ExtDataEntity2SimpleDtoConverter.class);
}
