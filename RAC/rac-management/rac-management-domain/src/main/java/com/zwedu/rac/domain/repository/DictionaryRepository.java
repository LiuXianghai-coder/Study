package com.zwedu.rac.domain.repository;


import com.zwedu.rac.domain.common.page.Pagination;
import com.zwedu.rac.domain.entity.DictionaryEntity;

import java.util.List;

/**
 * 字典
 *
 * @author qingchuan
 * @date 2020/12/10
 */
public interface DictionaryRepository {
    /**
     * 查询字典列表数据
     *
     * @param pageNo    当前页码
     * @param pageSize  分页大小
     * @param searchVal 检索值
     * @return 字典列表数据
     */
    Pagination<DictionaryEntity> listPage(Integer pageNo, Integer pageSize, Long bizLineId, String searchVal);


    /**
     * 查询业务线对应的字典
     *
     * @param bizLineId 业务线ID
     * @return 字典列表
     */
    List<DictionaryEntity> listByBizLineId(Long bizLineId);

    /**
     * 根据ID查询实体信息
     *
     * @param id ID
     * @return 实体信息
     */
    DictionaryEntity queryById(Long id);

    /**
     * 根据ID查询实体信息
     *
     * @param bizLineId 业务线ID
     * @param id        ID
     * @return 实体信息
     */
    DictionaryEntity queryById(Long bizLineId, Long id);

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
     * 新增字典
     *
     * @param record 字典实体
     */
    void insert(DictionaryEntity record);

    /**
     * 更新字典
     *
     * @param record 字典实体
     */
    void edit(DictionaryEntity record);

    /**
     * 删除字典
     *
     * @param record 字典实体
     */
    void delete(DictionaryEntity record);

    /**
     * 根据英文名查询
     *
     * @param bizLineId 业务线ID
     * @param enName    英文名
     * @return 字典实体
     */
    DictionaryEntity queryByEnName(Long bizLineId, String enName);
}
