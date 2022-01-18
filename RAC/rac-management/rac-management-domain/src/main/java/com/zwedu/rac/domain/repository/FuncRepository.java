package com.zwedu.rac.domain.repository;

import com.zwedu.rac.domain.entity.FuncEntity;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 功能
 *
 * @author qingchuan
 * @date 2020/12/10
 */
public interface FuncRepository {
    /**
     * 查询功能列表数据
     *
     * @param bizLineId 业务线ID
     * @param roleIds   角色ID列表
     * @return 功能列表数据
     */
    List<FuncEntity> listByRoleIds(Long bizLineId, Collection<Long> roleIds);

    /**
     * 查询子功能
     *
     * @param bizLineId 业务线ID
     * @param parentId  父节点ID
     * @return 功能列表数据
     */
    List<FuncEntity> listByParentId(Long bizLineId, Long parentId);

    /**
     * 查询子功能
     *
     * @param bizLineId 业务线ID
     * @param searchVal 检索值
     * @return 功能列表数据
     */
    List<FuncEntity> listByBizLineId(Long bizLineId, String searchVal);


    /**
     * 查询子功能
     *
     * @param bizLineId 业务线ID
     * @return 功能列表数据
     */
    List<FuncEntity> listByBizLineId(Long bizLineId);

    /**
     * 根据ID查询实体信息
     *
     * @param id ID
     * @return 实体信息
     */
    FuncEntity queryById(Long id);


    /**
     * 根据ID查询实体信息
     *
     * @param bizLineId 业务线ID
     * @param id        ID
     * @return 实体信息
     */
    FuncEntity queryById(Long bizLineId, Long id);

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
     * 新增功能
     *
     * @param record 功能实体
     */
    void insert(FuncEntity record);

    /**
     * 更新功能
     *
     * @param record 功能实体
     */
    void edit(FuncEntity record);

    /**
     * 删除功能
     *
     * @param record 功能实体
     */
    void delete(FuncEntity record);

    /**
     * 根据ID查询功能
     *
     * @param bizLineId 业务线ID
     * @param funcIds   功能ID列表
     * @return 功能列表
     */
    List<FuncEntity> listByIds(Long bizLineId, Collection<Long> funcIds);

    /**
     * 根据路径查询所有子节点
     *
     * @param bizLineId   业务线ID
     * @param parentPaths 父路径列表
     * @return 功能节点
     */
    List<FuncEntity> listByParentPaths(Long bizLineId, Collection<String> parentPaths);

    /**
     * 更新节点的parent path
     *
     * @param bizLineId     业务线ID
     * @param newParentPath 节点的新的父路径前缀
     * @param oldParentPath 节点的老的父路径前缀
     * @param ids           节点ID列表
     */
    void changeNodeWithNewPath(Long bizLineId, String newParentPath, String oldParentPath, Set<Long> ids);
}
