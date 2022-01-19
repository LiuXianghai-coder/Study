package com.zwedu.rac.application.converter;

import java.util.List;

/**
 * 业务线dto-entity转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
public interface Dto2EntityConverter<F, T> {

    /**
     * dto 转 entity
     *
     * @param record dto对象
     * @return 实体对象
     */
    T toEntity(F record);

    /**
     * dtoList 转 entityList
     *
     * @param recordList dto列表对象
     * @return 实体对象
     */
    List<T> toEntityList(List<F> recordList);
}
