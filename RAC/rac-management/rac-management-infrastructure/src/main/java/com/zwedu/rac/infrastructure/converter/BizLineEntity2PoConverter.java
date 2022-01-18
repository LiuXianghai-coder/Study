package com.zwedu.rac.infrastructure.converter;

import com.zwedu.rac.domain.entity.BizLineEntity;
import com.zwedu.rac.infrastructure.po.BizLinePo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 业务线po-entity转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface BizLineEntity2PoConverter extends Entity2PoConverter<BizLineEntity, BizLinePo> {
    /**
     * 实例
     */
    BizLineEntity2PoConverter INSTANCE = Mappers.getMapper(BizLineEntity2PoConverter.class);
}
