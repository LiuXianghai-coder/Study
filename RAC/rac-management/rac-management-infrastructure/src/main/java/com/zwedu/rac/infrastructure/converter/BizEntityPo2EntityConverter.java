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
public interface BizEntityPo2EntityConverter extends Po2EntityConverter<BizEntityPo, BizEntity>{
    /**
     * 实例
     */
    BizEntityPo2EntityConverter INSTANCE = Mappers.getMapper(BizEntityPo2EntityConverter.class);
}
