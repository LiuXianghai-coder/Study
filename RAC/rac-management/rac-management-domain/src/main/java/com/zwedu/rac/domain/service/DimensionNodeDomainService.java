package com.zwedu.rac.domain.service;

import com.google.common.collect.ImmutableSet;
import com.zwedu.rac.domain.common.annotation.NoneAuth;
import com.zwedu.rac.domain.common.validator.BizAssert;
import com.zwedu.rac.domain.common.validator.ParamAssert;
import com.zwedu.rac.domain.entity.DimensionEntity;
import com.zwedu.rac.domain.entity.DimensionNodeEntity;
import com.zwedu.rac.domain.repository.BizLineRepository;
import com.zwedu.rac.domain.repository.DictionaryNodeRepository;
import com.zwedu.rac.domain.repository.DimensionNodeRepository;
import com.zwedu.rac.domain.repository.DimensionRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 维度服务接口
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Service
public class DimensionNodeDomainService {
    @Resource
    private DimensionNodeRepository dimensionNodeRepository;
    @Resource
    private DictionaryNodeRepository dictionaryNodeRepository;
    @Resource
    private BizLineRepository bizLineRepository;
    @Resource
    private DimensionRepository dimensionRepository;

    /**
     * 查询业务线对应的维度节点
     *
     * @param bizLineId   业务线ID
     * @param dimensionId 维度ID
     * @param parentId    维度节点ID
     * @return 维度节点列表
     */
    public List<DimensionNodeEntity> listByParentId(Long bizLineId, Long dimensionId, Long parentId) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(bizLineId, dimensionId, parentId);
        return dimensionNodeRepository.listByParentId(bizLineId, dimensionId, parentId);
    }

    /**
     * 查询业务线对应的维度节点
     *
     * @param bizLineId      业务线ID
     * @param dimensionId    维度ID
     * @param parentPathList 父路径列表
     * @return 维度节点列表
     */
    @NoneAuth
    public List<DimensionNodeEntity> listByParentPaths(Long bizLineId, Long dimensionId,
                                                       Collection<String> parentPathList) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(bizLineId, dimensionId, parentPathList);
        return dimensionNodeRepository.listByParentPaths(bizLineId, dimensionId, parentPathList);
    }

    /**
     * 查询业务线对应的维度节点
     *
     * @param bizLineId   业务线ID
     * @param dimensionId 维度ID
     * @param searchVal   检索值
     * @return 维度节点列表
     */
    public List<DimensionNodeEntity> listByDimensionId(Long bizLineId, Long dimensionId, String searchVal) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(bizLineId, dimensionId);
        return dimensionNodeRepository.listByDimensionId(bizLineId, dimensionId, searchVal);
    }

    /**
     * 创建维度
     *
     * @param currentUserId 登录用户ID
     * @param record        维度实体
     */
    @Transactional(rollbackFor = Throwable.class)
    public void create(Long currentUserId, DimensionNodeEntity record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, record);
        DimensionNodeEntity parentNode = dimensionNodeRepository.queryById(record.getParentId());
        record.create(currentUserId, parentNode);
        // 所属业务线是否存在
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(bizLineRepository.queryById(record.getBizLineId()), "业务线");
        // 检查维度是否存在
        DimensionEntity dimensionEntity = dimensionRepository.queryById(record.getBizLineId(), record.getDimensionId());
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(dimensionEntity, "维度");
        // 检查维度节点类型值是否存在
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(dictionaryNodeRepository.containsValue(record.getBizLineId(),
                dimensionEntity.getNodeTypeId(), record.getType().toString()), "维度节点类型");
        // 检查英文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(dimensionNodeRepository
                .hasSameEnName(record.getBizLineId(), record.getDimensionId(), record.getEnName(),
                        record.getId()), "英文名");
        // 检查中文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(dimensionNodeRepository
                .hasSameCnName(record.getBizLineId(), record.getDimensionId(), record.getCnName(),
                        record.getId()), "中文名");
        dimensionNodeRepository.insert(record);
    }

    /**
     * 更新维度
     *
     * @param currentUserId 登录用户ID
     * @param record        维度实体
     */
    @Transactional(rollbackFor = Throwable.class)
    public void edit(Long currentUserId, DimensionNodeEntity record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, record);
        // 所属业务线是否存在
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(bizLineRepository.queryById(record.getBizLineId()), "业务线");
        // 检查维度是否存在
        DimensionEntity dimensionEntity = dimensionRepository.queryById(record.getBizLineId(), record.getDimensionId());
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(dimensionEntity, "维度");
        // 检查维度节点类型值是否存在
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(dictionaryNodeRepository.containsValue(record.getBizLineId(),
                dimensionEntity.getNodeTypeId(), record.getType().toString()), "节点类型");
        // 检查英文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(dimensionNodeRepository
                .hasSameEnName(record.getBizLineId(), record.getDimensionId(), record.getEnName(),
                        record.getId()), "英文名");
        // 检查中文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(dimensionNodeRepository
                .hasSameCnName(record.getBizLineId(), record.getDimensionId(), record.getCnName(),
                        record.getId()), "中文名");
        // 确认记录是否存在
        DimensionNodeEntity oldRecord = dimensionNodeRepository.queryById(record.getBizLineId(),
                record.getDimensionId(), record.getId());
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(oldRecord);
        // 获取父节点信息
        DimensionNodeEntity parentNodeRecord = dimensionNodeRepository.queryById(record.getBizLineId(),
                record.getDimensionId(), record.getParentId());
        record.edit(currentUserId, parentNodeRecord);
        // 如果修改了父节点，则进行修改子节点路径和环形验证
        if (!oldRecord.getParentId().equals(record.getParentId())) {
            // 获取子节点
            List<DimensionNodeEntity> childList = dimensionNodeRepository.listByParentPaths(record.getBizLineId(),
                    record.getDimensionId(),
                    ImmutableSet.of(oldRecord.getPath()));
            // 子节点不为空，则进行环形验证
            if (CollectionUtils.isNotEmpty(childList)) {
                Set<Long> childIds = childList.stream().map(input -> input.getId()).collect(Collectors.toSet());
                // 环形验证
                BizAssert.LOOP_TREE_ERROR.notTrue(isLoop(record.getParentId(), childIds));
                // 修改节点的parent_path
                dimensionNodeRepository.changeNodeWithNewPath(record.getBizLineId(), record.getDimensionId(),
                        record.getPath(), oldRecord.getPath(), childIds);
            }
        }
        dimensionNodeRepository.edit(record);
    }


    /**
     * 删除维度
     *
     * @param currentUserId 登录用户ID
     * @param id            记录ID
     */
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long currentUserId, Long bizLineId, Long dictionaryId, Long id) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, bizLineId, dictionaryId, id);
        DimensionNodeEntity record = dimensionNodeRepository.queryById(bizLineId, dictionaryId, id);
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(record);
        record.delete(currentUserId);
        dimensionNodeRepository.delete(record);
    }

    /**
     * 绑定主题节点和客体节点的关系
     *
     * @param currentUserId 当前操作用户
     * @param bizLineId     业务线ID
     * @param dimensionId   维度ID
     * @param subjectNodeId 主体节点ID
     * @param objectNodeId  客体节点ID
     */
    @Transactional(rollbackFor = Throwable.class)
    public void bindObjectNode(Long currentUserId, Long bizLineId, Long dimensionId,
                               Long subjectNodeId, Long objectNodeId) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, bizLineId, dimensionId, subjectNodeId, objectNodeId);
        BizAssert.LOOP_TREE_ERROR.notTrue(subjectNodeId.longValue() == objectNodeId.longValue());
        DimensionNodeEntity subjectNodeRecord = dimensionNodeRepository
                .queryById(bizLineId, dimensionId, subjectNodeId);
        DimensionNodeEntity objectNodeRecord = dimensionNodeRepository
                .queryById(bizLineId, dimensionId, objectNodeId);
        BizAssert.NOT_EXIST_RECORD_ERROR.allNotNull(subjectNodeRecord, objectNodeRecord, "主体节点或客体节点");

        // 获取子节点
        List<DimensionNodeEntity> childList = dimensionNodeRepository.listByParentPaths(bizLineId,
                dimensionId,
                ImmutableSet.of(subjectNodeRecord.getPath()));
        // 子节点不为空，则进行环形验证
        if (CollectionUtils.isNotEmpty(childList)) {
            Set<Long> childIds = childList.stream().map(input -> input.getId()).collect(Collectors.toSet());
            // 环形验证
            BizAssert.LOOP_TREE_ERROR.notTrue(isLoop(subjectNodeRecord.getId(), childIds));
        }
        dimensionNodeRepository.bindObjectNode(currentUserId, subjectNodeId, objectNodeId);
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
     * 解绑主题节点和客体节点的关系
     *
     * @param currentUserId 当前操作用户
     * @param bizLineId     业务线ID
     * @param dimensionId   维度ID
     * @param subjectNodeId 主体节点ID
     * @param objectNodeId  客体节点ID
     */
    @Transactional(rollbackFor = Throwable.class)
    public void unbindObjectNode(Long currentUserId, Long bizLineId, Long dimensionId,
                                 Long subjectNodeId, Long objectNodeId) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, bizLineId, dimensionId, subjectNodeId, objectNodeId);
        DimensionNodeEntity subjectNodeRecord = dimensionNodeRepository
                .queryById(bizLineId, dimensionId, subjectNodeId);
        DimensionNodeEntity objectNodeRecord = dimensionNodeRepository
                .queryById(bizLineId, dimensionId, objectNodeId);
        BizAssert.NOT_EXIST_RECORD_ERROR.allNotNull(subjectNodeRecord, objectNodeRecord, "主体节点或客体节点");
        dimensionNodeRepository.unbindObjectNode(subjectNodeId, objectNodeId);
    }


    /**
     * 获取客体节点ID
     *
     * @param subjectNodeId 主体节点ID
     * @return 客体节点ID
     */
    public Long queryObjectNodeId(Long bizLineId, Long dimensionId, Long subjectNodeId) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(bizLineId, dimensionId, subjectNodeId);
        DimensionNodeEntity subjectNodeRecord = dimensionNodeRepository
                .queryById(bizLineId, dimensionId, subjectNodeId);
        BizAssert.NOT_EXIST_RECORD_ERROR.allNotNull(subjectNodeRecord, "主体节点");
        return dimensionNodeRepository.queryObjectNodeId(subjectNodeId);
    }

    /**
     * 根据ID查询维度节点详情
     *
     * @param bizLineId   业务线ID
     * @param dimensionId 维度ID
     * @param id          ID
     * @return 维度节点ID
     */
    public DimensionNodeEntity queryById(Long bizLineId, Long dimensionId, Long id) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(bizLineId, dimensionId, id);
        return dimensionNodeRepository.queryById(bizLineId, dimensionId, id);
    }

    /**
     * 根据ID查询维度节点
     *
     * @param id 节点
     */
    public DimensionNodeEntity queryById(Long id) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(id);
        return dimensionNodeRepository.queryById(id);
    }

    /**
     * 通过ID查询维度节点
     *
     * @param dimensionNodeIds 维度节点ID
     * @return 维度节点
     */
    public List<DimensionNodeEntity> listDimensionNodeByIds(List<Long> dimensionNodeIds) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(dimensionNodeIds);
        return dimensionNodeRepository.listDimensionNodeByIds(dimensionNodeIds);
    }

}
