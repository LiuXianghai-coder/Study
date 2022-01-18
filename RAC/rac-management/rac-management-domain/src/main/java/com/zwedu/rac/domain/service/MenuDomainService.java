package com.zwedu.rac.domain.service;

import com.google.common.collect.ImmutableSet;
import com.zwedu.rac.domain.common.annotation.NoneAuth;
import com.zwedu.rac.domain.common.validator.BizAssert;
import com.zwedu.rac.domain.common.validator.ParamAssert;
import com.zwedu.rac.domain.entity.MenuEntity;
import com.zwedu.rac.domain.repository.BizLineRepository;
import com.zwedu.rac.domain.repository.MenuRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 菜单服务接口
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Service
public class MenuDomainService {
    @Resource
    private MenuRepository menuRepository;
    @Resource
    private BizLineRepository bizLineRepository;

    /**
     * 查询菜单列表数据
     *
     * @param bizLineId 业务线ID
     * @param roleIds   角色ID列表
     * @return 菜单列表数据
     */
    @NoneAuth
    public List<MenuEntity> listByRoleIds(Long bizLineId, Collection<Long> roleIds) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(bizLineId, roleIds);
        return menuRepository.listByRoleIds(bizLineId, roleIds);
    }

    /**
     * 查询子菜单
     *
     * @param bizLineId 业务线ID
     * @param parentId  父节点ID
     * @return 菜单列表数据
     */
    public List<MenuEntity> listByParentId(Long bizLineId, Long parentId) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(bizLineId, parentId);
        return menuRepository.listByParentId(bizLineId, parentId);
    }

    /**
     * 查询菜单
     *
     * @param bizLineId 业务线ID
     * @return 列表数据
     */
    public List<MenuEntity> listByBizLineId(Long bizLineId, String searchVal) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(bizLineId);
        return menuRepository.listByBizLineId(bizLineId, searchVal);
    }

    /**
     * 创建菜单
     *
     * @param currentUserId 登录用户ID
     * @param record        菜单实体
     */
    @Transactional(rollbackFor = Throwable.class)
    public void create(Long currentUserId, MenuEntity record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, record);
        // 获取父节点信息
        MenuEntity parentNodeRecord = queryById(record.getParentId());
        record.create(currentUserId, parentNodeRecord);
        // 所属业务线是否存在
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(bizLineRepository.queryById(record.getBizLineId()), "业务线");
        // 检查英文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(menuRepository
                .hasSameEnName(record.getBizLineId(), record.getEnName(),
                        record.getId()), "英文名");
        // 检查中文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(menuRepository
                .hasSameCnName(record.getBizLineId(), record.getCnName(),
                        record.getId()), "中文名");
        menuRepository.insert(record);
    }

    /**
     * 根据ID查询菜单详情
     *
     * @param id ID
     * @return 菜单详情
     */
    public MenuEntity queryById(Long id) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(id);
        return menuRepository.queryById(id);
    }

    /**
     * 更新菜单
     *
     * @param currentUserId 登录用户ID
     * @param record        菜单实体
     */
    @Transactional(rollbackFor = Throwable.class)
    public void edit(Long currentUserId, MenuEntity record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, record);
        // 所属业务线是否存在
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(bizLineRepository.queryById(record.getBizLineId()), "业务线");
        // 检查英文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(menuRepository
                .hasSameEnName(record.getBizLineId(), record.getEnName(),
                        record.getId()), "英文名");
        // 检查中文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(menuRepository
                .hasSameCnName(record.getBizLineId(), record.getCnName(),
                        record.getId()), "中文名");
        // 确认记录是否存在
        MenuEntity oldRecord = menuRepository.queryById(record.getBizLineId(), record.getId());
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(oldRecord);
        // 获取父节点信息
        MenuEntity parentNodeRecord = menuRepository.queryById(record.getBizLineId(), record.getParentId());
        record.edit(currentUserId, parentNodeRecord);
        // 如果修改了父节点，则进行修改子节点路径和环形验证
        if (!oldRecord.getParentId().equals(record.getParentId())) {
            // 获取子节点
            List<MenuEntity> childList = menuRepository.listByParentPaths(record.getBizLineId(),
                    ImmutableSet.of(oldRecord.getPath()));
            // 子节点不为空，则进行环形验证
            if (CollectionUtils.isNotEmpty(childList)) {
                Set<Long> childIds = childList.stream().map(input -> input.getId()).collect(Collectors.toSet());
                // 环形验证
                BizAssert.LOOP_TREE_ERROR.notTrue(isLoop(record.getParentId(), childIds));
                // 修改节点的parent_path
                menuRepository.changeNodeWithNewPath(record.getBizLineId(),
                        record.getPath(), oldRecord.getPath(),  childIds);
            }
        }
        menuRepository.edit(record);
    }

    /**
     * 是否成环形，如果子节点中包含父节点，则任务成环形
     *
     * @param parentId 父节点
     * @param childIds 子节点
     * @return 环形
     */
    public Boolean isLoop(Long parentId, Set<Long> childIds) {
        return childIds.contains(parentId);
    }

    /**
     * 删除菜单
     *
     * @param currentUserId 登录用户ID
     * @param id            记录ID
     */
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long currentUserId, Long bizLineId, Long id) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, bizLineId, id);
        MenuEntity record = menuRepository.queryById(bizLineId, id);
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(record);
        record.delete(currentUserId);
        menuRepository.delete(record);
    }

    /**
     * 根据ID查询对应的菜单信息
     *
     * @param bizLineId 业务线ID
     * @param ids       ID列表
     * @return 菜单信息列表
     */
    public List<MenuEntity> listByIds(Long bizLineId, Collection<Long> ids) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(bizLineId, ids);
        return menuRepository.listByIds(bizLineId, ids);
    }
}
