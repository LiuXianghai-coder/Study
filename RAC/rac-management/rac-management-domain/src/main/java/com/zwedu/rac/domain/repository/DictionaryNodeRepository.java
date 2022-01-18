package com.zwedu.rac.domain.repository;


import com.zwedu.rac.domain.entity.DictionaryNodeEntity;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 字典节点
 *
 * @author qingchuan
 * @date 2020/12/10
 */
public interface DictionaryNodeRepository {

    /**
     * 查询字典节点
     *
     * @param bizLineId    业务线ID
     * @param dictionaryId 字典ID
     * @param parentId     字典节点ID
     * @return 字典节点列表
     */
    List<DictionaryNodeEntity> listByParentId(Long bizLineId, Long dictionaryId, Long parentId);

    /**
     * 查询业务线字典节点
     *
     * @param bizLineId    业务线ID
     * @param dictionaryId 字典ID
     * @param searchVal    检索值
     * @return 字典节点
     */
    List<DictionaryNodeEntity> listByDictionaryId(Long bizLineId, Long dictionaryId, String searchVal);

    /**
     * 查询字典节点
     *
     * @param bizLineId     业务线ID
     * @param dictionaryIds 字典IDs
     * @return 字典节点列表
     */
    List<DictionaryNodeEntity> listByDictionaryIds(List<Long> dictionaryIds);

    /**
     * 是否已经包含值
     *
     * @param bizLineId    业务线ID
     * @param dictionaryId 字典ID
     * @param value        字典节点值
     * @return 是否已经存在字典节点值
     */
    Boolean containsValue(Long bizLineId, Long dictionaryId, String value);

    /**
     * 根据ID查询实体信息
     *
     * @param id ID
     * @return 实体信息
     */
    DictionaryNodeEntity queryById(Long id);

    /**
     * 根据ID查询实体信息
     *
     * @param bizLineId    业务线ID
     * @param dictionaryId 字典ID
     * @param id           ID
     * @return 实体信息
     */
    DictionaryNodeEntity queryById(Long bizLineId, Long dictionaryId, Long id);

    /**
     * 是否有重名的数据
     *
     * @param bizLineId    业务线ID
     * @param dictionaryId 字典ID
     * @param enName       英文名
     * @param id           数据ID
     * @return true or false
     */
    Boolean hasSameEnName(Long bizLineId, Long dictionaryId, String enName, Long id);


    /**
     * 是否有重名的数据
     *
     * @param bizLineId    业务线ID
     * @param dictionaryId 字典ID
     * @param cnName       中文名
     * @param id           数据ID
     * @return true or false
     */
    Boolean hasSameCnName(Long bizLineId, Long dictionaryId, String cnName, Long id);

    /**
     * 新增字典节点
     *
     * @param record 字典节点实体
     */
    void insert(DictionaryNodeEntity record);

    /**
     * 更新字典节点
     *
     * @param record 字典节点实体
     */
    void edit(DictionaryNodeEntity record);

    /**
     * 删除字典节点
     *
     * @param record 字典节点实体
     */
    void delete(DictionaryNodeEntity record);

    /**
     * 更新节点的parent path
     *
     * @param bizLineId     业务线ID
     * @param dictionaryId  字典ID
     * @param newParentPath 节点的新的父路径前缀
     * @param oldParentPath 节点的老的父路径前缀
     * @param ids           节点ID列表
     */
    void changeNodeWithNewPath(Long bizLineId, Long dictionaryId,
                               String newParentPath, String oldParentPath, Set<Long> ids);

    /**
     * 根据父路径查询
     *
     * @param bizLineId    业务线ID
     * @param dictionaryId 字典ID
     * @param parentPaths  父路径列表
     * @return 节点列表
     */
    List<DictionaryNodeEntity> listByParentPaths(Long bizLineId, Long dictionaryId, Collection<String> parentPaths);
}
