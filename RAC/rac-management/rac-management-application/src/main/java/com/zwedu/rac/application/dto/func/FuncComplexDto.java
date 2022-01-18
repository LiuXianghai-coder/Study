package com.zwedu.rac.application.dto.func;

import com.zwedu.rac.application.dto.BaseComplexDto;

/**
 * 功能传输对象
 *
 * @author qingchuan
 * @date 2020/12/9
 */
public class FuncComplexDto extends BaseComplexDto<FuncComplexDto> {
    /**
     * 功能URL
     */
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
