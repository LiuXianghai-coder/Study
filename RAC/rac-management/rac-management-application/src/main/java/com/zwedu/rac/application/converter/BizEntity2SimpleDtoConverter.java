package com.zwedu.rac.application.converter;

import com.zwedu.rac.application.dto.bizentity.BizEntitySimpleDto;
import com.zwedu.rac.domain.entity.BizEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 业务实体entity-dto转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface BizEntity2SimpleDtoConverter extends Entity2DtoConverter<BizEntity, BizEntitySimpleDto> {
    /**
     * 实例
     */
    BizEntity2SimpleDtoConverter INSTANCE = Mappers.getMapper(BizEntity2SimpleDtoConverter.class);
}
