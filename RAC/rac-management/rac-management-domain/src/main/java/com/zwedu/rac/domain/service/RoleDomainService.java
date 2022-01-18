package com.zwedu.rac.domain.service;

import com.zwedu.rac.domain.common.annotation.NoneAuth;
import com.zwedu.rac.domain.common.page.Pagination;
import com.zwedu.rac.domain.common.validator.BizAssert;
import com.zwedu.rac.domain.common.validator.ParamAssert;
import com.zwedu.rac.domain.entity.FuncEntity;
import com.zwedu.rac.domain.entity.MenuEntity;
import com.zwedu.rac.domain.entity.RoleEntity;
import com.zwedu.rac.domain.entity.StrategyEntity;
import com.zwedu.rac.domain.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色服务
 *
 * @author qingchuan
 * @date 2020/12/11
 */
@Service
@Slf4j
public class RoleDomainService {

    @Resource
    private RoleRepository roleRepository;
    @Resource
    private BizLineRepository bizLineRepository;
    @Resource
    private FuncRepository funcRepository;
    @Resource
    private MenuRepository menuRepository;
    @Resource
    private StrategyRepository strategyRepository;

    /**
     * 获取用户对应的角色列表
     *
     * @param bizLineId 角色ID
     * @param userId    用户ID
     * @return 角色列表
     */
    @NoneAuth
    public List<RoleEntity> listByUserId(Long bizLineId, Long userId) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(bizLineId, userId);
        // 获取用户对应的角色角色列表
        List<RoleEntity> roleList = roleRepository.listByUserId(bizLineId, userId);
        return roleList;
    }

    /**
     * 根据业务线ID查询角色列表
     *
     * @param pageNo    页码
     * @param pageSize  分页大小
     * @param bizLineId 业务线ID
     * @param searchVal 检索值
     * @return 角色列表
     */

    public Pagination<RoleEntity> listPage(Integer pageNo, Integer pageSize, Long bizLineId, String searchVal) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(pageNo, pageSize, bizLineId);
        return roleRepository.listPage(pageNo, pageSize, bizLineId, searchVal);
    }

    /**
     * 查询管辖角色列表
     *
     * @return 管辖角色列表
     */
    public List<RoleEntity> listByBizLineId(Long bizLineId) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(bizLineId);
        List<RoleEntity> roleList = roleRepository.listByBizLineId(bizLineId);
        return roleList;
    }

    /**
     * 查询角色授权信息
     *
     * @param pageNo   当前页
     * @param pageSize 每页大小
     * @param roleId   角色ID
     * @return 授权列表
     */
    public Pagination<Pair<Long, Long>> listAuth(Integer pageNo, Integer pageSize, Long bizLineId, Long roleId) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(pageNo, pageSize, bizLineId, roleId);
        return roleRepository.listAuth(pageNo, pageSize, bizLineId, roleId);
    }

    /**
     * 查询业务线角色绑定的菜单ID
     *
     * @param bizLineId 业务线ID
     * @param roleId    角色ID
     * @return 绑定ID列表
     */
    public List<Long> listBindMenuId(Long bizLineId, Long roleId) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(bizLineId, roleId);
        return roleRepository.listBindMenuId(bizLineId, roleId);
    }

    /**
     * 创建角色
     *
     * @param currentUserId 登录用户ID
     * @param record        角色实体
     */
    @Transactional(rollbackFor = Throwable.class)
    public void create(Long currentUserId, RoleEntity record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, record);
        record.create(currentUserId);
        // 所属业务线是否存在
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(bizLineRepository.queryById(record.getBizLineId()), "业务线");
        // 检查英文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(roleRepository
                .hasSameEnName(record.getBizLineId(), record.getEnName(),
                        record.getId()), "英文名");
        // 检查中文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(roleRepository
                .hasSameCnName(record.getBizLineId(), record.getCnName(),
                        record.getId()), "中文名");
        roleRepository.insert(record);
    }


    /**
     * 给角色绑定功能
     *
     * @param currentUserId 登录用户ID
     * @param bizLineId     业务线ID
     * @param roleId        角色ID
     * @param funcIds       功能ID
     * @param strategyId    功能策略ID列表
     */
    @Transactional(rollbackFor = Throwable.class)
    public void bindAuth(Long currentUserId, Long bizLineId, Long roleId, List<Long> funcIds, Long strategyId) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, roleId, funcIds, strategyId);
        // 获取角色列表
        RoleEntity roleEntity = roleRepository.queryById(bizLineId, roleId);
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(roleEntity);
        // 获取功能列表
        List<FuncEntity> funcEntityList = funcRepository.listByIds(bizLineId, funcIds);
        StrategyEntity strategyEntity = strategyRepository.queryById(bizLineId, strategyId);
        BizAssert.NOT_EXIST_RECORD_ERROR.isTrue(CollectionUtils.isNotEmpty(funcEntityList)
                && strategyEntity != null);
        roleRepository.bindAuth(currentUserId, roleId, funcEntityList.stream()
                .map(input -> input.getId()).collect(Collectors.toSet()), strategyId);
    }

    /**
     * 给角色绑定菜单
     *
     * @param currentUserId 登录用户ID
     * @param bizLineId     业务线ID
     * @param roleId        角色ID
     * @param menuIds       菜单ID
     */
    @Transactional(rollbackFor = Throwable.class)
    public void bindMenu(Long currentUserId, Long bizLineId, Long roleId, List<Long> menuIds) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, roleId, menuIds);
        // 获取角色列表
        RoleEntity roleEntity = roleRepository.queryById(bizLineId, roleId);
        // 获取功能列表
        List<MenuEntity> menuEntityList = menuRepository.listByIds(bizLineId, menuIds);
        BizAssert.NOT_EXIST_RECORD_ERROR.allNotNull(roleEntity, menuEntityList);
        roleRepository.bindMenu(currentUserId, roleId, menuEntityList.stream()
                .map(input -> input.getId()).collect(Collectors.toSet()));
    }

    /**
     * 给角色解绑功能
     *
     * @param roleId     角色ID
     * @param strategyId 功能策略ID列表
     */
    @Transactional(rollbackFor = Throwable.class)
    public void unbindAuth(Long bizLineId, Long roleId, List<Long> funcIds, Long strategyId) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(bizLineId, roleId, funcIds, strategyId);
        // 获取角色列表
        RoleEntity roleEntity = roleRepository.queryById(bizLineId, roleId);
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(roleEntity);
        // 获取功能列表
        List<FuncEntity> funcEntityList = funcRepository.listByIds(bizLineId, funcIds);
        StrategyEntity strategyEntity = strategyRepository.queryById(bizLineId, strategyId);
        BizAssert.NOT_EXIST_RECORD_ERROR.isTrue(CollectionUtils.isNotEmpty(funcEntityList)
                && strategyEntity != null);
        roleRepository.unbindAuth(roleId, funcEntityList.stream()
                .map(input -> input.getId()).collect(Collectors.toSet()), strategyId);
    }

    /**
     * 更新角色
     *
     * @param currentUserId 登录用户ID
     * @param record        角色实体
     */
    @Transactional(rollbackFor = Throwable.class)
    public void edit(Long currentUserId, RoleEntity record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, record);
        record.edit(currentUserId);
        // 所属业务线是否存在
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(bizLineRepository.queryById(record.getBizLineId()), "业务线");
        // 检查英文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(roleRepository
                .hasSameEnName(record.getBizLineId(), record.getEnName(),
                        record.getId()), "英文名");
        // 检查中文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(roleRepository
                .hasSameCnName(record.getBizLineId(), record.getCnName(),
                        record.getId()), "中文名");
        roleRepository.edit(record);
    }


    /**
     * 删除角色
     *
     * @param currentUserId 登录用户ID
     * @param id            记录ID
     */
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long currentUserId, Long bizLineId, Long id) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, bizLineId, id);
        RoleEntity record = roleRepository.queryById(bizLineId, id);
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(record);
        record.delete(currentUserId);
        roleRepository.delete(record);
    }

}
