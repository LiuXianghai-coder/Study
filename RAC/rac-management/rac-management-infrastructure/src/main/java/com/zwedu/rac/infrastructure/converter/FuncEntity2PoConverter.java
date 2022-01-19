package com.zwedu.rac.infrastructure.converter;

import com.zwedu.rac.domain.entity.FuncEntity;
import com.zwedu.rac.infrastructure.po.FuncPo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 功能po-entity转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface FuncEntity2PoConverter extends Entity2PoConverter<FuncEntity, FuncPo> {
    /**
     * 实例
     */
    FuncEntity2PoConverter INSTANCE = Mappers.getMapper(FuncEntity2PoConverter.class);
}
