package com.zwedu.rac.application.dto.dictionary;

import com.zwedu.rac.application.dto.BaseComplexDto;

/**
 * 字典传输对象
 *
 * @author qingchuan
 * @date 2020/12/9
 */
public class DictionaryComplexDto extends BaseComplexDto {

    /**
     * 是否使用扩展属性
     */
    private Integer useExtProperty;

    public Integer getUseExtProperty() {
        return useExtProperty;
    }

    public void setUseExtProperty(Integer useExtProperty) {
        this.useExtProperty = useExtProperty;
    }
}
