package com.zwedu.rac.application.converter;

import com.zwedu.rac.application.dto.dictionary.DictionaryComplexDto;
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
public interface DictionaryEntity2ComplexDtoConverter extends
        Entity2DtoConverter<DictionaryEntity, DictionaryComplexDto> {
    /**
     * 实例
     */
    DictionaryEntity2ComplexDtoConverter INSTANCE = Mappers.getMapper(DictionaryEntity2ComplexDtoConverter.class);
}
