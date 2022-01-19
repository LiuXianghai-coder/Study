package com.zwedu.rac.domain.entity;

import com.zwedu.rac.domain.common.enums.DecentralizedControlEnum;
import com.zwedu.rac.domain.common.validator.BizAssert;
import com.zwedu.rac.domain.entity.base.AbstractEntity;

import static com.zwedu.rac.domain.common.constant.SystemConstant.NORMAL_COLUMN_LENGTH;

/**
 * 业务线实体类
 *
 * @author qingchuan
 * @date 2020/12/9
 */
public class BizLineEntity extends AbstractEntity {

    /**
     * 英文名
     */
    private String enName;
    /**
     * 中文名
     */
    private String cnName;
    /**
     * 分级控制
     */
    private Integer decentralizedControl;
    /**
     * 需要分级管控时，分级管控需要配置对应的业务线的分级管控依赖的维度，按照对应的维度进行分级管控
     */
    private String decentralizedControlEnName;

    @Override
    public void validate() {

    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        BizAssert.PARAM_EMPTY_ERROR.notNull(enName, "用户英文名");
        BizAssert.PARAM_EMPTY_ERROR.isTrue(enName.length() < NORMAL_COLUMN_LENGTH, "用户英文名");
        this.enName = enName;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        BizAssert.PARAM_EMPTY_ERROR.notNull(cnName, "用户中文名");
        BizAssert.PARAM_EMPTY_ERROR.isTrue(cnName.length() < NORMAL_COLUMN_LENGTH, "用户中文名");
        this.cnName = cnName;
    }

    @Override
    public void create(Long currentUserId) {
        validate();
        completeForCreate(this, currentUserId);
    }


    @Override
    public void edit(Long currentUserId) {
        validate();
        completeForUpdate(this, currentUserId);
    }


    @Override
    public void delete(Long currentUserId) {
        validate();
        completeForDelete(this, currentUserId);
    }

    public Integer getDecentralizedControl() {
        return decentralizedControl;
    }

    public void setDecentralizedControl(Integer decentralizedControl) {
        BizAssert.PARAM_EMPTY_ERROR.isTrue(decentralizedControl != null
                && DecentralizedControlEnum.hasEnum(decentralizedControl), "分级管控");
        this.decentralizedControl = decentralizedControl;
    }

    public String getDecentralizedControlEnName() {
        return decentralizedControlEnName;
    }

    public void setDecentralizedControlEnName(String decentralizedControlEnName) {
        this.decentralizedControlEnName = decentralizedControlEnName;
    }
}
