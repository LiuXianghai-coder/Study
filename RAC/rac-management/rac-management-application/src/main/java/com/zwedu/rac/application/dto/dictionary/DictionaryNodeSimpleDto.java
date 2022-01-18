package com.zwedu.rac.application.dto.dictionary;

import com.zwedu.rac.application.dto.BaseSimpleDto;

/**
 * 字典节点传输对象
 *
 * @author qingchuan
 * @date 2020/12/9
 */
public class DictionaryNodeSimpleDto extends BaseSimpleDto {
    /**
     * 值
     */
    private String value;
    /**
     * 字典ID
     */
    private Long dictionaryId;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(Long dictionaryId) {
        this.dictionaryId = dictionaryId;
    }
}
