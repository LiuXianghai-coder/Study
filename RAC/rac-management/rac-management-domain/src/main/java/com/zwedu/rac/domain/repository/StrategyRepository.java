package com.zwedu.rac.domain.repository;


import com.zwedu.rac.domain.common.page.Pagination;
import com.zwedu.rac.domain.entity.StrategyEntity;

import java.util.Collection;
import java.util.List;

/**
 * 访问策略
 *
 * @author qingchuan
 * @date 2020/12/10
 */
public interface StrategyRepository {
    /**
     * 查询访问策略列表数据
     *
     * @param pageNo    当前页码
     * @param pageSize  分页大小
     * @param searchVal 检索值
     * @return 访问策略列表数据
     */
    Pagination<StrategyEntity> listPage(Integer pageNo, Integer pageSize, Long bizLineId, String searchVal);


    /**
     * 查询业务线对应的访问策略
     *
     * @param bizLineId 业务线ID
     * @return 访问策略列表
     */
    List<StrategyEntity> listByBizLineId(Long bizLineId);

    /**
     * 根据ID查询实体信息
     *
     * @param id ID
     * @return 实体信息
     */
    StrategyEntity queryById(Long id);

    /**
     * 根据ID查询实体信息
     *
     * @param bizLineId 业务线ID
     * @param id        ID
     * @return 实体信息
     */
    StrategyEntity queryById(Long bizLineId, Long id);

    /**
     * 是否有重名的数据
     *
     * @param bizLineId 业务线ID
     * @param enName    英文名
     * @param id        数据ID
     * @return true or false
     */
    Boolean hasSameEnName(Long bizLineId, String enName, Long id);

    /**
     * 是否有重名的数据
     *
     * @param bizLineId 业务线ID
     * @param cnName    中文名
     * @param id        数据ID
     * @return true or false
     */
    Boolean hasSameCnName(Long bizLineId, String cnName, Long id);

    /**
     * 新增访问策略
     *
     * @param record 访问策略实体
     */
    void insert(StrategyEntity record);

    /**
     * 更新访问策略
     *
     * @param record 访问策略实体
     */
    void edit(StrategyEntity record);

    /**
     * 删除访问策略
     *
     * @param record 访问策略实体
     */
    void delete(StrategyEntity record);

    /**
     * 根据ID查询
     *
     * @param bizLineId 业务线ID
     * @param ids       id 列表
     * @return 策略列表
     */
    List<StrategyEntity> listByIds(Long bizLineId, Collection<Long> ids);
}
