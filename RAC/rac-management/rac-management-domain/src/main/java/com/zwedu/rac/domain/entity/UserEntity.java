package com.zwedu.rac.domain.entity;

import com.zwedu.rac.domain.common.enums.GenderEnum;
import com.zwedu.rac.domain.common.enums.UserStatusEnum;
import com.zwedu.rac.domain.common.validator.BizAssert;
import com.zwedu.rac.domain.entity.base.BaseEntity;

import static com.zwedu.rac.domain.common.constant.SystemConstant.NORMAL_COLUMN_LENGTH;

/**
 * 用户实体类
 *
 * @author qingchuan
 * @date 2020/12/9
 */
public class UserEntity extends BaseEntity {
    /**
     * 用户密码
     */
    private String password;
    /**
     * 身份证号
     */
    private String idCardNum;
    /**
     * 手机号
     */
    private String mobilePhone;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 分级管控ID
     */
    private Long decentralizedControlId;

    @Override
    public void validate() {
    }

    @Override
    public void create(Long currentLoginId) {
        validate();
        completeForCreate(this, currentLoginId);
    }

    @Override
    public void edit(Long currentLoginId) {
        validate();
        completeForUpdate(this, currentLoginId);
    }


    @Override
    public void delete(Long currentLoginId) {
        validate();
        completeForDelete(this, currentLoginId);
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        BizAssert.PARAM_EMPTY_ERROR.notNull(mobilePhone, "用户手机号");
        BizAssert.DATA_TOO_LONG_50_ERROR.isTrue(mobilePhone.length() < NORMAL_COLUMN_LENGTH, "用户手机号");
        this.mobilePhone = mobilePhone;
    }

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        BizAssert.PARAM_EMPTY_ERROR.notNull(email, "用户邮箱");
        BizAssert.DATA_TOO_LONG_50_ERROR.isTrue(email.length() < NORMAL_COLUMN_LENGTH, "用户邮箱");
        this.email = email;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        BizAssert.PARAM_EMPTY_ERROR.notNull(gender, "用户性别");
        BizAssert.INCORRECT_VALUE_ERROR.isTrue(GenderEnum.hasEnum(gender), "用户性别");
        this.gender = gender;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        BizAssert.PARAM_EMPTY_ERROR.notNull(status, "用户状态");
        BizAssert.INCORRECT_VALUE_ERROR.isTrue(UserStatusEnum.hasEnum(status), "用户状态");
        this.status = status;
    }

    public Long getDecentralizedControlId() {
        return decentralizedControlId;
    }

    public void setDecentralizedControlId(Long decentralizedControlId) {
        this.decentralizedControlId = decentralizedControlId;
    }
}
