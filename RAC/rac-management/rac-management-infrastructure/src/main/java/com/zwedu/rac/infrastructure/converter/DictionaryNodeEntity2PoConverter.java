package com.zwedu.rac.infrastructure.converter;

import com.zwedu.rac.domain.entity.DictionaryNodeEntity;
import com.zwedu.rac.infrastructure.po.DictionaryNodePo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 字典节点po-entity转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface DictionaryNodeEntity2PoConverter extends Entity2PoConverter<DictionaryNodeEntity, DictionaryNodePo> {
    /**
     * 实例
     */
    DictionaryNodeEntity2PoConverter INSTANCE = Mappers.getMapper(DictionaryNodeEntity2PoConverter.class);
}
