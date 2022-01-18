package com.zwedu.rac.application.converter;

import com.zwedu.rac.application.dto.bizentity.BizEntitySimpleDto;
import com.zwedu.rac.domain.entity.BizEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 业务实体dto-entity转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface BizEntitySimpleDto2EntityConverter extends Dto2EntityConverter<BizEntitySimpleDto, BizEntity> {
    /**
     * 实例
     */
    BizEntitySimpleDto2EntityConverter INSTANCE = Mappers.getMapper(BizEntitySimpleDto2EntityConverter.class);
}