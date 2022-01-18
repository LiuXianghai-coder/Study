package com.zwedu.rac.domain.repository;


import com.zwedu.rac.domain.common.page.Pagination;
import com.zwedu.rac.domain.entity.BizEntity;

import java.util.List;

/**
 * 业务实体
 *
 * @author qingchuan
 * @date 2020/12/10
 */
public interface BizEntityRepository {
    /**
     * 查询业务实体列表数据
     *
     * @param pageNo    当前页码
     * @param pageSize  分页大小
     * @param searchVal 检索值
     * @return 业务实体列表数据
     */
    Pagination<BizEntity> listPage(Integer pageNo, Integer pageSize, Long bizLineId, String searchVal);


    /**
     * 查询业务线对应的业务实体
     *
     * @param bizLineId 业务线ID
     * @return 业务实体列表
     */
    List<BizEntity> listByBizLineId(Long bizLineId);


    /**
     * 根据ID查询实体信息
     *
     * @param bizLineId 业务线ID
     * @param id        ID
     * @return 实体信息
     */
    BizEntity queryById(Long bizLineId, Long id);

    /**
     * 根据ID查询实体信息
     *
     * @param id ID
     * @return 实体信息
     */
    BizEntity queryById(Long id);

    /**
     * 根据英文名查询实体信息
     *
     * @param enName 英文名
     * @return 实体信息
     */
    BizEntity queryByEnName(Long bizLineId, String enName);


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
     * 新增业务实体
     *
     * @param record 业务实体实体
     */
    void insert(BizEntity record);

    /**
     * 更新业务实体
     *
     * @param record 业务实体实体
     */
    void edit(BizEntity record);

    /**
     * 删除业务实体
     *
     * @param record 业务实体实体
     */
    void delete(BizEntity record);

}
