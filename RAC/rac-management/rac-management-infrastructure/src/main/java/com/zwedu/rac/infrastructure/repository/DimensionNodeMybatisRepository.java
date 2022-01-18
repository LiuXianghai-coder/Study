package com.zwedu.rac.infrastructure.repository;

import com.google.common.collect.Lists;
import com.zwedu.rac.domain.common.AuthInfoThreadLocal;
import com.zwedu.rac.domain.common.util.Collections2;
import com.zwedu.rac.domain.entity.DimensionNodeEntity;
import com.zwedu.rac.domain.repository.DimensionNodeRepository;
import com.zwedu.rac.infrastructure.converter.DimensionNodeEntity2PoConverter;
import com.zwedu.rac.infrastructure.converter.DimensionNodePo2EntityConverter;
import com.zwedu.rac.infrastructure.mapper.DimensionNodeControlMapper;
import com.zwedu.rac.infrastructure.mapper.DimensionNodeMapper;
import com.zwedu.rac.infrastructure.po.DimensionNodeControlPo;
import com.zwedu.rac.infrastructure.po.DimensionNodePo;
import com.zwedu.rac.infrastructure.po.IdNumPo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 维度节点存储类
 *
 * @author qingchuan
 * @date 2020/12/10
 */
@Repository
public class DimensionNodeMybatisRepository implements DimensionNodeRepository {
    @Resource
    private DimensionNodeMapper dimensionNodeMapper;
    @Resource
    private DimensionNodeControlMapper dimensionNodeControlMapper;

    @Override
    public List<DimensionNodeEntity> listByParentId(Long bizLineId, Long dimensionId, Long parentId) {
        List<DimensionNodePo> poList = dimensionNodeMapper.listByParentId(bizLineId, dimensionId, parentId,
                AuthInfoThreadLocal.AUTH_INFO.get());
        if (CollectionUtils.isEmpty(poList)) {
            return Lists.newArrayList();
        }
        Set<Long> idSet = poList.stream().map(input -> input.getId()).collect(Collectors.toSet());
        List<IdNumPo> childCountList = dimensionNodeMapper.countChildByIds(bizLineId, dimensionId, idSet);
        Map<Long, Long> id2ChildCountMap = Collections2.toMap(childCountList, input -> input.getId(),
                input -> input.getChildCount());
        return DimensionNodePo2EntityConverter.INSTANCE.toEntityList(poList, id2ChildCountMap);
    }

    @Override
    public List<DimensionNodeEntity> listByParentPaths(Long bizLineId, Long dimensionId, Collection<String> parentPathList) {
        List<DimensionNodePo> poList = dimensionNodeMapper.listByParentPaths(bizLineId, dimensionId, parentPathList);
        return DimensionNodePo2EntityConverter.INSTANCE.toEntityList(poList);
    }

    @Override
    public List<DimensionNodeEntity> listByDimensionId(Long bizLineId, Long dimensionId, String searchVal) {
        List<DimensionNodePo> poList = dimensionNodeMapper.listByDimensionId(bizLineId, dimensionId, searchVal);
        List<DimensionNodeEntity> retList = DimensionNodePo2EntityConverter.INSTANCE.toEntityList(poList);
        if (StringUtils.isNotEmpty(searchVal) && CollectionUtils.isNotEmpty(retList)) {
            // 如果检索值不为空，并且查询出结果，则按照节点path查找子节点
            List<DimensionNodePo> childList = dimensionNodeMapper.listByParentPaths(bizLineId,
                    dimensionId, Collections2.toList(retList, input -> input.getPath()));
            retList.addAll(DimensionNodePo2EntityConverter.INSTANCE.toEntityList(childList));
        }
        return retList;
    }

    @Override
    public DimensionNodeEntity queryById(Long id) {
        DimensionNodePo record = dimensionNodeMapper.selectByPrimaryKey(id);
        return DimensionNodePo2EntityConverter.INSTANCE.toEntity(record);
    }

    @Override
    public DimensionNodeEntity queryById(Long bizLineId, Long dimensionId, Long id) {
        DimensionNodePo record = dimensionNodeMapper.queryById(bizLineId, dimensionId, id);
        return DimensionNodePo2EntityConverter.INSTANCE.toEntity(record);
    }

    @Override
    public void bindObjectNode(Long currentUserId, Long subjectNodeId, Long objectNodeId) {
        dimensionNodeControlMapper.bindObjectNode(currentUserId, subjectNodeId, objectNodeId);
    }

    @Override
    public void unbindObjectNode(Long subjectNodeId, Long objectNodeId) {
        dimensionNodeControlMapper.unbindObjectNode(subjectNodeId, objectNodeId);
    }

    @Override
    public Long queryObjectNodeId(Long subjectNodeId) {
        DimensionNodeControlPo controlPo = dimensionNodeControlMapper.queryControl(subjectNodeId);
        return controlPo == null ? null : controlPo.getObjectNodeId();
    }

    @Override
    public List<DimensionNodeEntity> listDimensionNodeByIds(List<Long> dimensionNodeIds) {
        List<DimensionNodePo> poList = dimensionNodeMapper.listDimensionNodeByIds(dimensionNodeIds);
        return DimensionNodePo2EntityConverter.INSTANCE.toEntityList(poList);
    }

    @Override
    public Boolean hasSameEnName(Long bizLineId, Long dimensionId, String enName, Long id) {
        return dimensionNodeMapper.queryByEnName(bizLineId, dimensionId, enName, id) != null;
    }

    @Override
    public Boolean hasSameCnName(Long bizLineId, Long dimensionId, String cnName, Long id) {
        return dimensionNodeMapper.queryByCnName(bizLineId, dimensionId, cnName, id) != null;
    }

    @Override
    public void insert(DimensionNodeEntity record) {
        dimensionNodeMapper.insertSelective(DimensionNodeEntity2PoConverter.INSTANCE.toPo(record));
    }

    /**
     * 更新维度节点
     *
     * @param record 维度节点实体
     */
    @Override
    public void edit(DimensionNodeEntity record) {
        dimensionNodeMapper.updateByPrimaryKeySelective(DimensionNodeEntity2PoConverter.INSTANCE.toPo(record));
    }


    /**
     * 删除维度节点
     *
     * @param record 维度节点实体
     */
    @Override
    public void delete(DimensionNodeEntity record) {
        dimensionNodeMapper.updateByPrimaryKeySelective(DimensionNodeEntity2PoConverter.INSTANCE.toPo(record));
    }

    @Override
    public void changeNodeWithNewPath(Long bizLineId, Long dimensionId,
                                      String newParentPath, String oldParentPath, Set<Long> ids) {
        dimensionNodeMapper.changeNodeWithNewPath(bizLineId, dimensionId, newParentPath, oldParentPath, ids);
    }
}
