package com.zwedu.rac.domain.repository;

import com.zwedu.rac.domain.entity.MenuEntity;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 菜单
 *
 * @author qingchuan
 * @date 2020/12/10
 */
public interface MenuRepository {
    /**
     * 查询菜单列表数据
     *
     * @param bizLineId 业务线ID
     * @param roleIds   角色ID列表
     * @return 菜单列表数据
     */
    List<MenuEntity> listByRoleIds(Long bizLineId, Collection<Long> roleIds);

    /**
     * 查询子菜单
     *
     * @param bizLineId 业务线ID
     * @param parentId  父节点ID
     * @return 菜单列表数据
     */
    List<MenuEntity> listByParentId(Long bizLineId, Long parentId);

    /**
     * 查询业务线菜单
     *
     * @param bizLineId 业务线ID
     * @param searchVal 检索值
     * @return 菜单列表
     */
    List<MenuEntity> listByBizLineId(Long bizLineId, String searchVal);


    /**
     * 查询业务线菜单
     *
     * @param bizLineId 业务线ID
     * @param menuIds   菜单IDs
     * @return 菜单列表
     */
    List<MenuEntity> listByIds(Long bizLineId, Collection<Long> menuIds);

    /**
     * 根据ID查询实体信息
     *
     * @param id ID
     * @return 实体信息
     */
    MenuEntity queryById(Long id);

    /**
     * 根据ID查询实体信息
     *
     * @param bizLineId 业务线ID
     * @param id        ID
     * @return 实体信息
     */
    MenuEntity queryById(Long bizLineId, Long id);

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
     * 新增菜单
     *
     * @param record 菜单实体
     */
    void insert(MenuEntity record);

    /**
     * 更新菜单
     *
     * @param record 菜单实体
     */
    void edit(MenuEntity record);

    /**
     * 删除菜单
     *
     * @param record 菜单实体
     */
    void delete(MenuEntity record);

    /**
     * 根据path查询子节点
     *
     * @param bizLineId   业务线ID
     * @param parentPaths 父路径列表
     * @return 子节点
     */
    List<MenuEntity> listByParentPaths(Long bizLineId, Collection<String> parentPaths);

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
