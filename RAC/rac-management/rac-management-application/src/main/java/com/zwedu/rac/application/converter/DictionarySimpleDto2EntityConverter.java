package com.zwedu.rac.application.converter;

import com.zwedu.rac.application.dto.dictionary.DictionarySimpleDto;
import com.zwedu.rac.domain.entity.DictionaryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 字典dto-entity转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface DictionarySimpleDto2EntityConverter extends Dto2EntityConverter<DictionarySimpleDto, DictionaryEntity> {
    /**
     * 实例
     */
    DictionarySimpleDto2EntityConverter INSTANCE = Mappers.getMapper(DictionarySimpleDto2EntityConverter.class);
}