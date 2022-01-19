package com.zwedu.rac.application.converter;

import com.zwedu.rac.application.dto.bizline.BizLineComplexDto;
import com.zwedu.rac.domain.entity.BizLineEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 业务线entity-dto转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface BizLineEntity2ComplexDtoConverter extends
        Entity2DtoConverter<BizLineEntity, BizLineComplexDto> {
    /**
     * 实例
     */
    BizLineEntity2ComplexDtoConverter INSTANCE = Mappers.getMapper(BizLineEntity2ComplexDtoConverter.class);
}
