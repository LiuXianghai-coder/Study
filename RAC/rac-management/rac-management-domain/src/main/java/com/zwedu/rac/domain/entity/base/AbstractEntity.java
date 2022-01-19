package com.zwedu.rac.domain.entity.base;

import com.zwedu.rac.domain.common.enums.DeletedEnum;
import com.zwedu.rac.domain.common.validator.BizAssert;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

import static com.zwedu.rac.domain.common.constant.SystemConstant.MIN_COLUMN_LENGTH;

/**
 * 基类
 *
 * @author qingchuan
 * @date 2020/12/9
 */
public abstract class AbstractEntity {
    /**
     * ID
     */
    private Long id;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private Long createUserId;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 更新人
     */
    private Long updateUserId;
    /**
     * 删除状态
     */
    private Integer deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        BizAssert.DATA_TOO_LONG_255_ERROR.isTrue(StringUtils.isEmpty(remark) || (StringUtils.isNotEmpty(remark)
                && remark.length() < MIN_COLUMN_LENGTH), "备注");
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    /**
     * 校验方法
     */
    public abstract void validate();


    /**
     * 创建填充方法
     *
     * @param root          聚合根
     * @param currentLoginId 用户ID
     * @param <R>           聚合根类型
     */
    public <R extends AbstractEntity> void completeForCreate(R root, Long currentLoginId) {
        root.setDeleted(DeletedEnum.NO.getValue());
        root.setCreateUserId(currentLoginId);
        root.setCreateTime(new Date());
        root.setUpdateUserId(currentLoginId);
        root.setUpdateTime(new Date());
    }

    /**
     * 更新填充方法
     *
     * @param root          聚合根
     * @param currentLoginId 用户ID
     * @param <R>           聚合根类型
     */
    public <R extends AbstractEntity> void completeForUpdate(R root, Long currentLoginId) {
        BizAssert.PARAM_EMPTY_ERROR.notNull(this.getId(), "ID");
        root.setUpdateUserId(currentLoginId);
        root.setUpdateTime(new Date());
    }

    /**
     * 删除填充方法
     *
     * @param root          聚合根
     * @param currentLoginId 用户ID
     * @param <R>           聚合根类型
     */
    public <R extends AbstractEntity> void completeForDelete(R root, Long currentLoginId) {
        BizAssert.PARAM_EMPTY_ERROR.notNull(this.getId(), "ID");
        root.setDeleted(DeletedEnum.YES.getValue());
        root.setUpdateUserId(currentLoginId);
        root.setUpdateTime(new Date());
    }

    /**
     * 是否可用
     *
     * @return true 删除  false 未删除
     */
    public boolean isDeleted() {
        BizAssert.PARAM_EMPTY_ERROR.notNull(this.getDeleted(), "删除状态");
        return DeletedEnum.YES.getValue() == this.getDeleted();
    }

    /**
     * 创建方法
     *
     * @param currentLoginId 登录用户ID
     */
    public abstract void create(Long currentLoginId);

    /**
     * 编辑方法
     *
     * @param currentLoginId 登录用户ID
     */
    public abstract void edit(Long currentLoginId);

    /**
     * 删除方法
     *
     * @param currentLoginId 登录用户ID
     */
    public abstract void delete(Long currentLoginId);
}
