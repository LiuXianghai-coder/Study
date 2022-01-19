package com.zwedu.rac.infrastructure.converter;

/**
 * po-entity转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
public interface Entity2PoConverter<F,T> {
    /**
     * po 转 entity
     *
     * @param record po对象
     * @return 实体对象
     */
    T toPo(F record);
}
