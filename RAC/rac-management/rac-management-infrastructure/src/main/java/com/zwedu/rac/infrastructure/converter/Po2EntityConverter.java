package com.zwedu.rac.infrastructure.converter;

import java.util.List;

/**
 * po-entity转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
public interface Po2EntityConverter<F, T> {
    /**
     * po 转 entity
     *
     * @param record po对象
     * @return 实体对象
     */
    T toEntity(F record);

    /**
     * poList 转 entityList
     *
     * @param recordList po列表对象
     * @return 实体对象
     */
    List<T> toEntityList(List<F> recordList);
}
