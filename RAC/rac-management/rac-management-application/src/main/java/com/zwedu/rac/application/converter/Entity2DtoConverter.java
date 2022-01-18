package com.zwedu.rac.application.converter;

import com.zwedu.rac.application.dto.ResPaginationDto;
import com.zwedu.rac.domain.common.page.Pagination;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * 业务线entity-dto转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
public interface Entity2DtoConverter<F, T> {
    /**
     * 实体 转 DTO
     *
     * @param record 实体对象
     * @return dto对象
     */
    T toDto(F record);

    /**
     * entityList 转 dtoList
     *
     * @param recordList 实体列表对象
     * @return dto对象
     */
    List<T> toDtoList(List<F> recordList);

    /**
     * 分页dto
     *
     * @param pagination 分页对象
     * @return 分页dto
     */
    @Mappings({
            @Mapping(target = "dataList", expression = "java(toDtoList(pagination.getDataList()))")
    })
    ResPaginationDto<T> toPaginationDto(Pagination<F> pagination);
}
