package com.zwedu.rac.application.converter;

import com.zwedu.rac.application.dto.dictionary.DictionarySimpleDto;
import com.zwedu.rac.domain.entity.DictionaryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 字典entity-dto转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface DictionaryEntity2SimpleDtoConverter extends Entity2DtoConverter
        <DictionaryEntity, DictionarySimpleDto> {
    /**
     * 实例
     */
    DictionaryEntity2SimpleDtoConverter INSTANCE = Mappers.getMapper(DictionaryEntity2SimpleDtoConverter.class);
}
