package com.zwedu.rac.domain.entity;

import com.zwedu.rac.domain.entity.base.BaseEntity;

/**
 * 业务实体类
 *
 * @author qingchuan
 * @date 2020/12/9
 */
public class BizEntity extends BaseEntity {

    @Override
    public void validate() {

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
}
