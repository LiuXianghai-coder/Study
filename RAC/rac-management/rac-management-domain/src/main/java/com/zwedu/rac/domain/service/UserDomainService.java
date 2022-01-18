package com.zwedu.rac.domain.service;

import com.zwedu.rac.domain.common.annotation.NoneAuth;
import com.zwedu.rac.domain.common.page.Pagination;
import com.zwedu.rac.domain.common.validator.BizAssert;
import com.zwedu.rac.domain.common.validator.ParamAssert;
import com.zwedu.rac.domain.entity.DimensionNodeEntity;
import com.zwedu.rac.domain.entity.UserEntity;
import com.zwedu.rac.domain.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户服务接口
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Service
public class UserDomainService {

    @Resource
    private UserRepository userRepository;
    @Resource
    private BizLineRepository bizLineRepository;
    @Resource
    private RoleRepository roleRepository;
    @Resource
    private DimensionNodeRepository dimensionNodeRepository;
    @Resource
    private ExtPropertyRepository extPropertyRepository;

    /**
     * 查询用户列表数据
     *
     * @param pageNo    页码
     * @param pageSize  分页大小
     * @param bizLineId 业务线ID
     * @param searchVal 检索值
     * @return 用户列表数据
     */
    public Pagination<UserEntity> listPage(Long currentLoginId, Integer pageNo, Integer pageSize,
                                           Long bizLineId, String searchVal) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(pageNo, pageSize, bizLineId);
        return userRepository.listPage(currentLoginId, pageNo, pageSize, bizLineId, searchVal);
    }

    /**
     * 创建用户
     *
     * @param currentLoginId 登录用户ID
     * @param record        用户实体
     */
    @Transactional(rollbackFor = Throwable.class)
    public void create(Long currentLoginId, UserEntity record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentLoginId, record);
        record.create(currentLoginId);
        // 所属业务线是否存在
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(bizLineRepository.queryById(record.getBizLineId()), "业务线");
        // 检查英文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(userRepository
                .hasSameEnName(record.getEnName(),
                        record.getId()), "英文名");
        userRepository.insert(record);
    }


    /**
     * 给用户绑定角色
     *
     * @param currentLoginId 登录用户ID
     * @param userId        用户ID
     * @param roleIds       角色ID列表
     */
    @Transactional(rollbackFor = Throwable.class)
    public void bindRoles(Long currentLoginId, Long userId, List<Long> roleIds) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentLoginId, userId, roleIds);
        userRepository.bindRoles(currentLoginId, userId, roleIds);
    }

    /**
     * 更新用户
     *
     * @param currentLoginId 登录用户ID
     * @param record        用户实体
     */
    @Transactional(rollbackFor = Throwable.class)
    public void edit(Long currentLoginId, UserEntity record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentLoginId, record);
        record.edit(currentLoginId);
        // 所属业务线是否存在
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(bizLineRepository.queryById(record.getBizLineId()), "业务线");
        // 英文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(userRepository.hasSameEnName(record.getEnName(),
                record.getId()), "英文名");
        UserEntity oldRecord = userRepository.queryById(record.getId());
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(oldRecord);
        userRepository.edit(record);
    }


    /**
     * 删除用户
     *
     * @param currentLoginId 登录用户ID
     * @param id            记录ID
     */
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long currentLoginId, Long id) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentLoginId, id);
        UserEntity record = userRepository.queryById(id);
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(record);
        record.delete(currentLoginId);
        userRepository.delete(record);
    }

    /**
     * 查询用户角色ID
     *
     * @param userId 用户ID
     */
    public List<Long> listUserRoleId(Long userId) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(userId);
        return userRepository.listUserRoleId(userId);
    }


    /**
     * 查询用户角色ID
     *
     * @param userId 用户ID
     */
    public List<DimensionNodeEntity> listUserDimensionNodes(Long userId) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(userId);
        return userRepository.listUserDimensionNodes(userId);
    }


    /**
     * 查询用户角色ID
     *
     * @param userId      用户ID
     * @param bizLineId   业务线ID
     * @param dimensionId 维度ID
     */
    @NoneAuth
    public List<DimensionNodeEntity> listUserDimensionNodes(Long userId, Long bizLineId, Long dimensionId) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(userId, bizLineId, dimensionId);
        return userRepository.listUserDimensionNodes(userId, bizLineId, dimensionId);
    }

    /**
     * 给用户绑定维度
     *
     * @param currentLoginId    登录用户ID
     * @param currentLoginId    登录用户ID
     * @param dimensionNodeIds 角色ID列表
     */
    @Transactional(rollbackFor = Throwable.class)
    public void bindDimensionNodes(Long currentLoginId, Long userId, List<Long> dimensionNodeIds) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentLoginId, userId, dimensionNodeIds);
        userRepository.bindDimensionNodes(currentLoginId, userId, dimensionNodeIds);
    }

    /**
     * 返回用户实体
     *
     * @param enName 英文名
     * @return 用户实体
     */
    public UserEntity queryByEnName(String enName) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(enName);
        return userRepository.queryByEnName(enName);
    }

    /**
     * 根据用户ID查询用户基本信息
     *
     * @param id 用户ID
     */
    @NoneAuth
    public UserEntity queryById(Long id) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(id);
        return userRepository.queryById(id);
    }
}
