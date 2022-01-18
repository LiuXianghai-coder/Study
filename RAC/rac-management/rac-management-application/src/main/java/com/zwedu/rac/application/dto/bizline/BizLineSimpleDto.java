package com.zwedu.rac.application.dto.bizline;

import com.zwedu.rac.application.dto.BaseSimpleDto;

/**
 * 业务线传输对象
 *
 * @author qingchuan
 * @date 2020/12/9
 */
public class BizLineSimpleDto extends BaseSimpleDto {
    /**
     * 分级控制
     */
    private Integer decentralizedControl;
    /**
     * 分级管控英文名
     */
    private String decentralizedControlEnName;

    public Integer getDecentralizedControl() {
        return decentralizedControl;
    }

    public void setDecentralizedControl(Integer decentralizedControl) {
        this.decentralizedControl = decentralizedControl;
    }

    public String getDecentralizedControlEnName() {
        return decentralizedControlEnName;
    }

    public void setDecentralizedControlEnName(String decentralizedControlEnName) {
        this.decentralizedControlEnName = decentralizedControlEnName;
    }
}
