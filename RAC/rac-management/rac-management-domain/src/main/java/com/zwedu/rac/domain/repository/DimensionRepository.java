package com.zwedu.rac.domain.repository;


import com.zwedu.rac.domain.common.page.Pagination;
import com.zwedu.rac.domain.entity.DimensionEntity;

import java.util.List;

/**
 * 维度实体
 *
 * @author qingchuan
 * @date 2020/12/10
 */
public interface DimensionRepository {
    /**
     * 查询维度实体列表数据
     *
     * @param pageNo    当前页码
     * @param pageSize  分页大小
     * @param searchVal 检索值
     * @return 维度实体列表数据
     */
    Pagination<DimensionEntity> listPage(Integer pageNo, Integer pageSize, Long bizLineId, String searchVal);


    /**
     * 查询维度线对应的维度实体
     *
     * @param bizLineId 维度线ID
     * @return 维度实体列表
     */
    List<DimensionEntity> listByBizLineId(Long bizLineId);

    /**
     * 根据ID查询实体信息
     *
     * @param id ID
     * @return 实体信息
     */
    DimensionEntity queryById(Long id);

    /**
     * 根据ID查询实体信息
     *
     * @param bizLineId 业务线ID
     * @param id        ID
     * @return 实体信息
     */
    DimensionEntity queryById(Long bizLineId, Long id);

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
     * 通过英文名查询
     *
     * @param bizLineId 业务线ID
     * @param enName    英文名
     * @return 维度实体
     */
    DimensionEntity queryByEnName(Long bizLineId, String enName);

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
     * 新增维度实体
     *
     * @param record 维度实体实体
     */
    void insert(DimensionEntity record);

    /**
     * 更新维度实体
     *
     * @param record 维度实体实体
     */
    void edit(DimensionEntity record);

    /**
     * 删除维度实体
     *
     * @param record 维度实体实体
     */
    void delete(DimensionEntity record);

}
