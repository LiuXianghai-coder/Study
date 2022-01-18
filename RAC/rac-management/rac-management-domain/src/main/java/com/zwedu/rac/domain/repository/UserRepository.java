package com.zwedu.rac.domain.repository;


import com.zwedu.rac.domain.common.page.Pagination;
import com.zwedu.rac.domain.entity.DimensionNodeEntity;
import com.zwedu.rac.domain.entity.UserEntity;

import java.util.List;


/**
 * 用户存储类
 *
 * @author qingchuan
 * @date 2020/12/10
 */
public interface UserRepository {
    /**
     * 查询用户列表数据
     *
     * @param pageNo    页码
     * @param pageSize  分页大小
     * @param bizLineId 业务线ID
     * @param searchVal 检索值
     * @return 用户列表数据
     */
    Pagination<UserEntity> listPage(Long currentLoginId, Integer pageNo, Integer pageSize,
                                    Long bizLineId, String searchVal);

    /**
     * 查询用户授权的角色ID
     *
     * @param userId 用户ID
     */
    List<Long> listUserRoleId(Long userId);


    /**
     * 查询用户授权的维度节点ID
     *
     * @param userId 用户ID
     */
    List<DimensionNodeEntity> listUserDimensionNodes(Long userId);


    /**
     * 查询用户授权的维度节点ID
     *
     * @param userId      用户ID
     * @param bizLineId   业务线ID
     * @param dimensionId 维度ID
     */
    List<DimensionNodeEntity> listUserDimensionNodes(Long userId, Long bizLineId, Long dimensionId);

    /**
     * 根据ID查询实体信息
     *
     * @param id ID
     * @return 实体信息
     */
    UserEntity queryById(Long id);

    /**
     * 是否有重名的数据
     *
     * @param enName 英文名
     * @param id     数据ID
     * @return true or false
     */
    Boolean hasSameEnName(String enName, Long id);

    /**
     * 根据ID查询实体信息
     *
     * @param enName 英文名
     * @return 实体信息
     */
    UserEntity queryByEnName(String enName);

    /**
     * 绑定用户角色
     *
     * @param currentLoginId 登录用户ID
     * @param userId        用户ID
     * @param roleIds       角色ID列表
     */
    void bindRoles(Long currentLoginId, Long userId, List<Long> roleIds);

    /**
     * 给用户绑定维度
     *
     * @param currentLoginId    登录用户ID
     * @param currentLoginId    登录用户ID
     * @param dimensionNodeIds 角色ID列表
     */
    void bindDimensionNodes(Long currentLoginId, Long userId, List<Long> dimensionNodeIds);

    /**
     * 新增用户
     *
     * @param record 用户实体
     */
    void insert(UserEntity record);

    /**
     * 更新用户
     *
     * @param record 用户实体
     */
    void edit(UserEntity record);

    /**
     * 删除用户
     *
     * @param record 用户实体
     */
    void delete(UserEntity record);
}
