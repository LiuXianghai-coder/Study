package com.zwedu.rac.sdk.rdo;

import java.io.Serializable;

/**
 * 扩展属性响应数据对象
 *
 * @author qingchuan
 * @date 2020/12/23
 */
public class ExtPropertyRdo implements Serializable {
    /**
     * 属性名
     */
    private String name;
    /**
     * 属性值
     */
    private Object value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
