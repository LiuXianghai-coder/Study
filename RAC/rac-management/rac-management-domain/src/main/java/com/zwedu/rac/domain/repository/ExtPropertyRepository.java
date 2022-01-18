package com.zwedu.rac.domain.repository;


import com.zwedu.rac.domain.common.page.Pagination;
import com.zwedu.rac.domain.entity.ExtDataEntity;
import com.zwedu.rac.domain.entity.ExtPropertyEntity;

import java.util.List;

/**
 * 扩展属性
 *
 * @author qingchuan
 * @date 2020/12/10
 */
public interface ExtPropertyRepository {
    /**
     * 查询扩展属性列表数据
     *
     * @param pageNo    当前页码
     * @param pageSize  分页大小
     * @param searchVal 检索值
     * @return 扩展属性列表数据
     */
    Pagination<ExtPropertyEntity> listPage(Integer pageNo, Integer pageSize, Long bizLineId, String searchVal);


    /**
     * 查询业务线对应的扩展属性
     *
     * @param bizLineId 业务线ID
     * @return 扩展属性列表
     */
    List<ExtPropertyEntity> listByBizLineId(Long bizLineId);

    /**
     * 根据实体ID查询
     *
     * @param bizLineId   业务线ID
     * @param bizEntityId 实体ID
     * @return 实体扩展属性列表
     */
    List<ExtPropertyEntity> listByBizEntityId(Long bizLineId, Long bizEntityId);

    /**
     * 根据ID查询实体信息
     *
     * @param id ID
     * @return 实体信息
     */
    ExtPropertyEntity queryById(Long id);

    /**
     * 根据ID查询实体信息
     *
     * @param bizLineId   业务线ID
     * @param bizEntityId 业务实体ID
     * @param id          数据ID
     * @return true or false
     */
    ExtPropertyEntity queryById(Long bizLineId, Long bizEntityId, Long id);

    /**
     * 是否有重名的数据
     *
     * @param bizLineId   业务线ID
     * @param bizEntityId 业务实体ID
     * @param enName      英文名
     * @param id          数据ID
     * @return true or false
     */
    Boolean hasSameEnName(Long bizLineId, Long bizEntityId, String enName, Long id);

    /**
     * 是否有重名的数据
     *
     * @param bizLineId   业务线ID
     * @param bizEntityId 业务实体ID
     * @param cnName      中文名
     * @param id          数据ID
     * @return true or false
     */
    Boolean hasSameCnName(Long bizLineId, Long bizEntityId, String cnName, Long id);

    /**
     * 新增扩展属性
     *
     * @param record 扩展属性实体
     */
    void insert(ExtPropertyEntity record);

    /**
     * 更新扩展属性
     *
     * @param record 扩展属性实体
     */
    void edit(ExtPropertyEntity record);

    /**
     * 删除扩展属性
     *
     * @param record 扩展属性实体
     */
    void delete(ExtPropertyEntity record);

    /**
     * 通过ID列表查询
     *
     * @param ids ID列表
     * @return
     */
    List<ExtPropertyEntity> listByIds(List<Long> ids);

    /**
     * 扩展属性数据
     *
     * @param bizEntityId 业务实体ID
     * @param bizDataId   业务数据ID
     * @return 扩展属性列表
     */
    List<ExtDataEntity> listDataByBizId(Long bizEntityId, Long bizDataId);


    /**
     * 添加用户扩展属性
     *
     * @param record 记录
     */
    void addExtProperty(ExtDataEntity record);

    /**
     * 删除用户扩展属性
     *
     * @param record 记录
     */
    void dropExtProperty(ExtDataEntity record);

    /**
     * 查询用户扩展属性数据
     *
     * @param bizLineId 业务线ID
     * @param userId    用户ID
     * @return 扩展属性
     */
    List<ExtDataEntity> listExtProperty(Long bizLineId, Long userId);
}
