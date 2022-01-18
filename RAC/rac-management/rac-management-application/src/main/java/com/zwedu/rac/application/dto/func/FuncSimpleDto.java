package com.zwedu.rac.application.dto.func;

import com.zwedu.rac.application.dto.BaseSimpleDto;

/**
 * 功能传输对象
 *
 * @author qingchuan
 * @date 2020/12/9
 */
public class FuncSimpleDto extends BaseSimpleDto {
    /**
     * 功能内容
     */
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
