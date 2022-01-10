package org.xhliu.unionpay.dao;

import org.xhliu.unionpay.entity.OmsOrder;

public interface OmsOrderDao {
    int deleteByPrimaryKey(Long id);

    int insert(OmsOrder record);

    int insertSelective(OmsOrder record);

    OmsOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OmsOrder record);

    int updateByPrimaryKey(OmsOrder record);
}