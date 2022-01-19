package com.zwedu.rac.domain.service;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zwedu.rac.domain.common.annotation.NoneAuth;
import com.zwedu.rac.domain.common.util.Collections2;
import com.zwedu.rac.domain.common.validator.BizAssert;
import com.zwedu.rac.domain.common.validator.ParamAssert;
import com.zwedu.rac.domain.entity.FuncEntity;
import com.zwedu.rac.domain.entity.StrategyEntity;
import com.zwedu.rac.domain.repository.BizLineRepository;
import com.zwedu.rac.domain.repository.FuncRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 功能服务接口
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Service
public class FuncDomainService {
    @Resource
    private FuncRepository funcRepository;
    @Resource
    private BizLineRepository bizLineRepository;

    /**
     * 查询功能列表数据
     *
     * @param bizLineId 业务线ID
     * @param roleIds   角色ID列表
     * @return 功能列表数据
     */
    @NoneAuth
    public List<FuncEntity> listByRoleIds(Long bizLineId, Collection<Long> roleIds) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(bizLineId, roleIds);
        List<FuncEntity> funcEntityList = funcRepository.listByRoleIds(bizLineId, roleIds);
        if (CollectionUtils.isNotEmpty(funcEntityList)) {
            Map<Long, StrategyEntity> funcStrategyMap = Collections2.toMap(funcEntityList, input -> input.getId(),
                    input -> input.getStrategyEntity());
            List<FuncEntity> childEntityList = funcRepository.listByParentPaths(bizLineId,
                    funcEntityList.stream().map(input -> input.getPath()).collect(Collectors.toSet()));
            if (CollectionUtils.isNotEmpty(childEntityList)) {
                List<FuncEntity> sortedList = childEntityList.stream()
                        .sorted(Comparator.comparing(FuncEntity::getLevel)).collect(Collectors.toList());
                // 循环，设置对应的策略
                for (FuncEntity funcEntity : sortedList) {
                    funcEntity.setStrategyEntity(funcStrategyMap.get(funcEntity.getParentId()));
                    funcStrategyMap.put(funcEntity.getId(), funcStrategyMap.get(funcEntity.getParentId()));
                }
            }
        }
        return funcEntityList;
    }

    /**
     * 查询子功能
     *
     * @param bizLineId 业务线ID
     * @param parentId  父节点ID
     * @return 功能列表数据
     */
    public List<FuncEntity> listByParentId(Long bizLineId, Long parentId) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(bizLineId, parentId);
        return funcRepository.listByParentId(bizLineId, parentId);
    }


    /**
     * 查询功能
     *
     * @param bizLineId 业务线ID
     * @return 功能列表数据
     */
    public List<FuncEntity> listByBizLineId(Long bizLineId, String searchVal) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(bizLineId);
        return funcRepository.listByBizLineId(bizLineId, searchVal);
    }

    /**
     * 创建功能
     *
     * @param currentUserId 登录用户ID
     * @param record        功能实体
     */
    @Transactional(rollbackFor = Throwable.class)
    public void create(Long currentUserId, FuncEntity record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, record);
        // 通过业务线ID查询业务下的功能列比
        List<FuncEntity> funcEntityList = funcRepository.listByBizLineId(record.getBizLineId());
        // 获取父节点信息
        FuncEntity parentNodeRecord = funcRepository.queryById(record.getBizLineId(), record.getParentId());
        record.create(currentUserId, parentNodeRecord, funcEntityList);
        // 所属业务线是否存在
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(bizLineRepository.queryById(record.getBizLineId()), "业务线");
        // 检查英文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(funcRepository
                .hasSameEnName(record.getBizLineId(), record.getEnName(),
                        record.getId()), "英文名");
        // 检查中文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(funcRepository
                .hasSameCnName(record.getBizLineId(), record.getCnName(),
                        record.getId()), "中文名");
        funcRepository.insert(record);
    }

    /**
     * 根据ID查询功能详情
     *
     * @param id ID
     * @return 功能详情
     */
    public FuncEntity queryById(Long id) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(id);
        return funcRepository.queryById(id);
    }

    /**
     * 更新功能
     *
     * @param currentUserId 登录用户ID
     * @param record        功能实体
     */
    @Transactional(rollbackFor = Throwable.class)
    public void edit(Long currentUserId, FuncEntity record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, record);
        // 所属业务线是否存在
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(bizLineRepository
                .queryById(record.getBizLineId()), "业务线");
        // 检查英文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(funcRepository
                .hasSameEnName(record.getBizLineId(), record.getEnName(),
                        record.getId()), "英文名");
        // 检查中文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(funcRepository
                .hasSameCnName(record.getBizLineId(), record.getCnName(),
                        record.getId()), "中文名");
        // 确认记录是否存在
        FuncEntity oldRecord = funcRepository.queryById(record.getBizLineId(), record.getId());
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(oldRecord);
        List<FuncEntity> funcEntityList = funcRepository.listByBizLineId(record.getBizLineId());
        // 获取父节点信息
        FuncEntity parentNodeRecord = funcRepository.queryById(record.getBizLineId(), record.getParentId());
        record.edit(currentUserId, parentNodeRecord, funcEntityList);
        // 如果修改了父节点，则进行修改子节点路径和环形验证
        if (!oldRecord.getParentId().equals(record.getParentId())) {
            // 获取子节点
            List<FuncEntity> childList = funcRepository.listByParentPaths(record.getBizLineId(),
                    ImmutableSet.of(oldRecord.getPath()));
            // 子节点不为空，则进行环形验证
            if (CollectionUtils.isNotEmpty(childList)) {
                Set<Long> childIds = childList.stream().map(input -> input.getId()).collect(Collectors.toSet());
                // 环形验证
                BizAssert.LOOP_TREE_ERROR.notTrue(isLoop(record.getParentId(), childIds));
                // 修改所有子节点的parent_path
                funcRepository.changeNodeWithNewPath(record.getBizLineId(),
                        record.getPath(), oldRecord.getPath(), childIds);
            }
        }
        // 保存更新数据
        funcRepository.edit(record);
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
     * 删除功能
     *
     * @param currentUserId 登录用户ID
     * @param id            记录ID
     */
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long currentUserId, Long bizLineId, Long id) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, bizLineId, id);
        FuncEntity record = funcRepository.queryById(bizLineId, id);
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(record);
        record.delete(currentUserId);
        funcRepository.delete(record);
    }

    /**
     * 根据功能ID获取功能信息
     *
     * @param bizLineId 业务线ID
     * @param funcIds   功能ID
     */
    public Map<Long, FuncEntity> listByIds(Long bizLineId, Collection<Long> funcIds) {
        if (CollectionUtils.isEmpty(funcIds) || bizLineId == null) {
            return Maps.newHashMap();
        }
        List<FuncEntity> funcEntityList = funcRepository.listByIds(bizLineId, funcIds);
        return Collections2.toMap(funcEntityList, input -> input.getId(), input -> input);
    }

    /**
     * 通过父节点path查询对应的所有子节点
     *
     * @param bizLineId   业务线ID
     * @param parentPaths 父路径列表
     * @return 功能列表
     */
    public List<FuncEntity> listByParentPaths(Long bizLineId, Set<String> parentPaths) {
        if (CollectionUtils.isEmpty(parentPaths) || bizLineId == null) {
            return Lists.newArrayList();
        }
        return funcRepository.listByParentPaths(bizLineId, parentPaths);
    }
}
