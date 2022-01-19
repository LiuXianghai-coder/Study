package com.zwedu.rac.application.converter;

import java.util.List;

/**
 * 业务线entity-dto转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
public interface Entity2RdoConverter<F, T> {
    /**
     * 实体 转 rdo
     *
     * @param record 实体对象
     * @return dto对象
     */
    T toRdo(F record);

    /**
     * entityList 转 RdoList
     *
     * @param recordList 实体列表对象
     * @return dto对象
     */
    List<T> toRdoList(List<F> recordList);
}
