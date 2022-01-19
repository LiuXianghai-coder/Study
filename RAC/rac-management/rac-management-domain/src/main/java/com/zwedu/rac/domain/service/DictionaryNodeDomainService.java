package com.zwedu.rac.domain.service;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zwedu.rac.domain.common.util.Collections2;
import com.zwedu.rac.domain.common.validator.BizAssert;
import com.zwedu.rac.domain.common.validator.ParamAssert;
import com.zwedu.rac.domain.entity.DictionaryNodeEntity;
import com.zwedu.rac.domain.repository.BizLineRepository;
import com.zwedu.rac.domain.repository.DictionaryNodeRepository;
import com.zwedu.rac.domain.repository.DictionaryRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 字典服务接口
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Service
public class DictionaryNodeDomainService {
    @Resource
    private DictionaryNodeRepository dictionaryNodeRepository;
    @Resource
    private DictionaryRepository dictionaryRepository;
    @Resource
    private BizLineRepository bizLineRepository;

    /**
     * 查询业务线对应的字典节点
     *
     * @param bizLineId    业务线ID
     * @param dictionaryId 字典ID
     * @param parentId     字典节点ID
     * @return 字典节点列表
     */
    public List<DictionaryNodeEntity> listByParentId(Long bizLineId, Long dictionaryId, Long parentId) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(bizLineId, dictionaryId, parentId);
        return dictionaryNodeRepository.listByParentId(bizLineId, dictionaryId, parentId);
    }


    /**
     * 查询业务线对应的字典节点
     *
     * @param bizLineId    业务线ID
     * @param dictionaryId 字典ID
     * @param searchVal    检索值
     * @return 字典节点列表
     */
    public List<DictionaryNodeEntity> listByDictionaryId(Long bizLineId, Long dictionaryId, String searchVal) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(bizLineId, dictionaryId);
        return dictionaryNodeRepository.listByDictionaryId(bizLineId, dictionaryId, searchVal);
    }

    /**
     * 创建字典
     *
     * @param currentUserId 登录用户ID
     * @param record        字典实体
     */
    @Transactional(rollbackFor = Throwable.class)
    public void create(Long currentUserId, DictionaryNodeEntity record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, record);
        DictionaryNodeEntity parentNode = dictionaryNodeRepository.queryById(record.getParentId());
        record.create(currentUserId, parentNode);
        // 所属业务线是否存在
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(bizLineRepository.queryById(record.getBizLineId()), "业务线");
        // 检查字典是否存在
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(dictionaryRepository.queryById(record.getBizLineId(),
                record.getDictionaryId()), "字典");
        // 检查英文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(dictionaryNodeRepository
                .hasSameEnName(record.getBizLineId(), record.getDictionaryId(),
                        record.getEnName(), record.getId()), "英文名");
        // 检查中文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(dictionaryNodeRepository
                .hasSameCnName(record.getBizLineId(), record.getDictionaryId(), record.getCnName(),
                        record.getId()), "中文名");
        dictionaryNodeRepository.insert(record);
    }

    /**
     * 更新字典
     *
     * @param currentUserId 登录用户ID
     * @param record        字典实体
     */
    @Transactional(rollbackFor = Throwable.class)
    public void edit(Long currentUserId, DictionaryNodeEntity record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, record);
        DictionaryNodeEntity parentNode = dictionaryNodeRepository.queryById(record.getParentId());
        // 所属业务线是否存在
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(bizLineRepository.queryById(record.getBizLineId()), "业务线");
        // 检查字典是否存在
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(dictionaryRepository.queryById(record.getBizLineId(),
                record.getDictionaryId()), "字典");
        // 检查英文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(dictionaryNodeRepository
                .hasSameEnName(record.getBizLineId(), record.getDictionaryId(), record.getEnName(),
                        record.getId()), "英文名");
        // 检查中文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(dictionaryNodeRepository
                .hasSameCnName(record.getBizLineId(), record.getDictionaryId(), record.getCnName(),
                        record.getId()), "中文名");
        // 确认记录是否存在
        DictionaryNodeEntity oldRecord = dictionaryNodeRepository.queryById(record.getBizLineId(),
                record.getDictionaryId(), record.getId());
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(oldRecord);
        // 获取父节点信息
        DictionaryNodeEntity parentNodeRecord = dictionaryNodeRepository.queryById(record.getBizLineId(),
                record.getDictionaryId(), record.getParentId());
        record.edit(currentUserId, parentNodeRecord);
        // 如果修改了父节点，则进行修改子节点路径和环形验证
        if (!oldRecord.getParentId().equals(record.getParentId())) {
            // 获取子节点
            List<DictionaryNodeEntity> childList = dictionaryNodeRepository.listByParentPaths(record.getBizLineId(),
                    record.getDictionaryId(),
                    ImmutableSet.of(oldRecord.getPath()));
            // 子节点不为空，则进行环形验证
            if (CollectionUtils.isNotEmpty(childList)) {
                Set<Long> childIds = childList.stream().map(input -> input.getId()).collect(Collectors.toSet());
                // 环形验证
                BizAssert.LOOP_TREE_ERROR.notTrue(isLoop(record.getParentId(), childIds));
                // 修改节点的parent_path
                dictionaryNodeRepository.changeNodeWithNewPath(record.getBizLineId(), record.getDictionaryId(),
                        record.getPath(), oldRecord.getPath(),  childIds);
            }
        }
        dictionaryNodeRepository.edit(record);
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
     * 删除字典
     *
     * @param currentUserId 登录用户ID
     * @param id            记录ID
     */
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long currentUserId, Long bizLineId, Long dimensionId, Long id) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, bizLineId, dimensionId, id);
        DictionaryNodeEntity record = dictionaryNodeRepository.queryById(bizLineId, dimensionId, id);
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(record);
        record.delete(currentUserId);
        dictionaryNodeRepository.delete(record);
    }

    /**
     * 获取字典和字典节点映射关系
     *
     * @param dictionaryIds 字典ID
     * @return 映射关系
     */
    public Map<Long, Map<Integer, DictionaryNodeEntity>> listByDictionaryIds(Set<Long> dictionaryIds) {
        if (CollectionUtils.isEmpty(dictionaryIds)) {
            return Maps.newHashMap();
        }
        // 获取字典节点node
        List<DictionaryNodeEntity> nodeEntityList = dictionaryNodeRepository
                .listByDictionaryIds(Lists.newArrayList(dictionaryIds));
        return Collections2.toValueMap(nodeEntityList, input -> input.getDictionaryId(),
                input -> Integer.parseInt(input.getValue()));
    }
}
