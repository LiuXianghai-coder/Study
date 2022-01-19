package com.zwedu.rac.domain.repository;

import com.zwedu.rac.domain.common.page.Pagination;
import com.zwedu.rac.domain.entity.RoleEntity;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 角色存储层
 *
 * @author qingchuan
 * @date 2020/12/12
 */
public interface RoleRepository {
    /**
     * 根据用户ID和角色查询角色列表
     *
     * @param bizLineId 角色ID
     * @param userId    用户ID
     * @return 角色列表
     */
    List<RoleEntity> listByUserId(Long bizLineId, Long userId);


    /**
     * 根据用户ID和角色查询角色列表
     *
     * @param bizLineId 角色ID
     * @param pageNo    当前页码
     * @param pageSize  分页大小
     * @param searchVal 检索值
     * @return 角色列表
     */
    Pagination<RoleEntity> listPage(Integer pageNo, Integer pageSize, Long bizLineId, String searchVal);

    /**
     * 查询授权角色列表
     *
     * @return 授权角色列表
     */
    List<RoleEntity> listByBizLineId(Long bizLineId);

    /**
     * 根据ID查询实体信息
     *
     * @param id ID
     * @return 实体信息
     */
    RoleEntity queryById(Long id);

    /**
     * 根据ID查询实体信息
     *
     * @param bizLineId 业务线ID
     * @param id        ID
     * @return 实体信息
     */
    RoleEntity queryById(Long bizLineId, Long id);

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
     * 新增角色
     *
     * @param record 角色实体
     */
    void insert(RoleEntity record);


    /**
     * 给角色绑定功能
     *
     * @param currentUserId 登录用户ID
     * @param roleId        角色ID
     * @param funcIds       功能策略ID列表
     * @param strategyId    访问策略ID
     */
    void bindAuth(Long currentUserId, Long roleId, Collection<Long> funcIds, Long strategyId);

    /**
     * 绑定菜单
     *
     * @param currentUserId 当前用户
     * @param roleId        角色ID
     * @param menuIds       菜单Id
     */
    void bindMenu(Long currentUserId, Long roleId, Set<Long> menuIds);

    /**
     * 给角色绑定功能
     *
     * @param roleId     角色ID
     * @param funcIds    功能策略ID列表
     * @param strategyId 访问策略ID
     */
    void unbindAuth(Long roleId, Collection<Long> funcIds, Long strategyId);

    /**
     * 更新角色
     *
     * @param record 角色实体
     */
    void edit(RoleEntity record);

    /**
     * 删除角色
     *
     * @param record 角色实体
     */
    void delete(RoleEntity record);

    /**
     * 根据角色ID查询功能ID
     *
     * @param roleId 角色ID
     * @return 功能ID列表
     */
    Pagination<Pair<Long, Long>> listAuth(Integer pageNo, Integer pageSize, Long bizLineId, Long roleId);

    /**
     * 查询业务线角色绑定的菜单ID
     *
     * @param bizLineId 业务线ID
     * @param roleId    角色ID
     * @return 绑定ID列表
     */
    List<Long> listBindMenuId(Long bizLineId, Long roleId);

}
