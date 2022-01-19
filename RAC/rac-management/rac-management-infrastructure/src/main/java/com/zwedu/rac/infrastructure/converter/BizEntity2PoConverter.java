package com.zwedu.rac.infrastructure.converter;

import com.zwedu.rac.domain.entity.BizEntity;
import com.zwedu.rac.infrastructure.po.BizEntityPo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 业务实体po-entity转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface BizEntity2PoConverter extends Entity2PoConverter<BizEntity, BizEntityPo> {
    /**
     * 实例
     */
    BizEntity2PoConverter INSTANCE = Mappers.getMapper(BizEntity2PoConverter.class);
}
