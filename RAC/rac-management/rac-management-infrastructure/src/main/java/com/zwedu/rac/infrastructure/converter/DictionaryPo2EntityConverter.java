package com.zwedu.rac.infrastructure.converter;

import com.zwedu.rac.domain.entity.DictionaryEntity;
import com.zwedu.rac.infrastructure.po.DictionaryPo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 字典po-entity转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface DictionaryPo2EntityConverter extends Po2EntityConverter<DictionaryPo, DictionaryEntity> {
    /**
     * 实例
     */
    DictionaryPo2EntityConverter INSTANCE = Mappers.getMapper(DictionaryPo2EntityConverter.class);
}
