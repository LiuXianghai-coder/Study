package com.zwedu.rac.domain.entity;

import com.zwedu.rac.domain.common.validator.BizAssert;
import com.zwedu.rac.domain.entity.base.TreeEntity;

/**
 * 菜单实体类
 *
 * @author qingchuan
 * @date 2020/12/9
 */
public class MenuEntity extends TreeEntity {
    /**
     * 菜单URL
     */
    private String url;
    /**
     * 序号
     */
    private Integer seq;

    @Override
    public void validate() {
    }

    @Override
    public void create(Long currentUserId) {
        validate();
        completeForCreate(this, currentUserId);
    }

    /**
     * 新增逻辑 校验和填充默认值
     *
     * @param currentUserId 登录用户ID
     * @param parentNode    父节点信息
     */
    public void create(Long currentUserId, MenuEntity parentNode) {
        create(currentUserId);
        completePath(parentNode);
    }


    /**
     * 修改节点逻辑填充默认值和修改父节点逻辑处理
     *
     * @param currentUserId 登录用户ID
     * @param parentNode    父节点信息
     */
    public void edit(Long currentUserId, MenuEntity parentNode) {
        edit(currentUserId);
        completePath(parentNode);
    }


    @Override
    public void edit(Long currentUserId) {
        validate();
        BizAssert.LOOP_TREE_ERROR.notTrue(this.getId().longValue() == this.getParentId().longValue());
        completeForUpdate(this, currentUserId);
    }


    @Override
    public void delete(Long currentUserId) {
        validate();
        completeForDelete(this, currentUserId);
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        BizAssert.PARAM_EMPTY_ERROR.notNull(seq, "序号");
        this.seq = seq;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        BizAssert.PARAM_EMPTY_ERROR.notNull(url, "菜单URL");
        this.url = url;
    }
}
