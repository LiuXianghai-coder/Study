package com.zwedu.rac.infrastructure.repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.zwedu.rac.domain.common.AuthInfoThreadLocal;
import com.zwedu.rac.domain.common.strategy.entity.AuthInfo;
import com.zwedu.rac.domain.common.util.Collections2;
import com.zwedu.rac.domain.entity.DictionaryNodeEntity;
import com.zwedu.rac.domain.repository.DictionaryNodeRepository;
import com.zwedu.rac.infrastructure.converter.DictionaryNodeEntity2PoConverter;
import com.zwedu.rac.infrastructure.converter.DictionaryNodePo2EntityConverter;
import com.zwedu.rac.infrastructure.mapper.DictionaryNodeMapper;
import com.zwedu.rac.infrastructure.po.DictionaryNodePo;
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
 * 字典存储类
 *
 * @author qingchuan
 * @date 2020/12/10
 */
@Repository
public class DictionaryNodeMybatisRepository implements DictionaryNodeRepository {
    @Resource
    private DictionaryNodeMapper dictionaryNodeMapper;

    @Override
    public List<DictionaryNodeEntity> listByParentId(Long bizLineId, Long dictionaryId, Long parentId) {
        AuthInfo authInfo = AuthInfoThreadLocal.AUTH_INFO.get();
        List<DictionaryNodePo> poList = dictionaryNodeMapper.listByParentId(bizLineId, dictionaryId, parentId,
                authInfo);
        if (CollectionUtils.isEmpty(poList)) {
            return Lists.newArrayList();
        }
        Set<Long> idSet = poList.stream().map(input -> input.getId()).collect(Collectors.toSet());
        List<IdNumPo> childCountList = dictionaryNodeMapper.countChildByIds(bizLineId, dictionaryId, idSet);
        Map<Long, Long> id2ChildCountMap = Collections2.toMap(childCountList, input -> input.getId(),
                input -> input.getChildCount());
        return DictionaryNodePo2EntityConverter.INSTANCE.toEntityList(poList, id2ChildCountMap);
    }


    @Override
    public List<DictionaryNodeEntity> listByDictionaryId(Long bizLineId, Long dictionaryId, String searchVal) {
        AuthInfo authInfo = AuthInfoThreadLocal.AUTH_INFO.get();
        List<DictionaryNodePo> poList = dictionaryNodeMapper.listByDictionaryId(bizLineId, dictionaryId, searchVal,
                authInfo);
        List<DictionaryNodeEntity> retList = DictionaryNodePo2EntityConverter.INSTANCE.toEntityList(poList);
        if (StringUtils.isNotEmpty(searchVal) && CollectionUtils.isNotEmpty(retList)) {
            // 如果检索值不为空，并且查询出结果，则按照节点path查找子节点
            List<DictionaryNodePo> childList = dictionaryNodeMapper.listByParentPaths(bizLineId,
                    dictionaryId, Collections2.toList(retList, input -> input.getPath()));
            retList.addAll(DictionaryNodePo2EntityConverter.INSTANCE.toEntityList(childList));
        }
        return retList;
    }

    @Override
    public List<DictionaryNodeEntity> listByDictionaryIds(List<Long> dictionaryIds) {
        if (CollectionUtils.isEmpty(dictionaryIds)) {
            return Lists.newArrayList();
        }
        List<DictionaryNodePo> poList = dictionaryNodeMapper.listByDictionaryIds(dictionaryIds);
        return DictionaryNodePo2EntityConverter.INSTANCE.toEntityList(poList);
    }

    @Override
    public Boolean containsValue(Long bizLineId, Long dictionaryId, String value) {
        List<DictionaryNodePo> poList = dictionaryNodeMapper.listByDictionaryIds(
                ImmutableList.of(dictionaryId));
        if (CollectionUtils.isEmpty(poList)) {
            return false;
        }
        Set<String> values = poList.stream().map(input -> input.getValue()).collect(Collectors.toSet());
        return values.contains(value);
    }

    @Override
    public DictionaryNodeEntity queryById(Long id) {
        DictionaryNodePo record = dictionaryNodeMapper.selectByPrimaryKey(id);
        return DictionaryNodePo2EntityConverter.INSTANCE.toEntity(record);
    }

    @Override
    public DictionaryNodeEntity queryById(Long bizLineId, Long dictionaryId, Long id) {
        DictionaryNodePo record = dictionaryNodeMapper.queryById(bizLineId, dictionaryId, id);
        return DictionaryNodePo2EntityConverter.INSTANCE.toEntity(record);
    }

    @Override
    public Boolean hasSameEnName(Long bizLineId, Long dictionaryId, String enName, Long id) {
        return dictionaryNodeMapper.queryByEnName(bizLineId, dictionaryId, enName, id) != null;
    }


    @Override
    public Boolean hasSameCnName(Long bizLineId, Long dictionaryId, String cnName, Long id) {
        return dictionaryNodeMapper.queryByCnName(bizLineId, dictionaryId, cnName, id) != null;
    }

    @Override
    public void insert(DictionaryNodeEntity record) {
        dictionaryNodeMapper.insertSelective(DictionaryNodeEntity2PoConverter.INSTANCE.toPo(record));
    }

    /**
     * 更新字典
     *
     * @param record 字典实体
     */
    @Override
    public void edit(DictionaryNodeEntity record) {
        dictionaryNodeMapper.updateByPrimaryKeySelective(DictionaryNodeEntity2PoConverter.INSTANCE.toPo(record));
    }


    /**
     * 删除字典
     *
     * @param record 字典实体
     */
    @Override
    public void delete(DictionaryNodeEntity record) {
        dictionaryNodeMapper.updateByPrimaryKeySelective(DictionaryNodeEntity2PoConverter.INSTANCE.toPo(record));
    }

    @Override
    public void changeNodeWithNewPath(Long bizLineId, Long dictionaryId,
                                      String newParentPath, String oldParentPath, Set<Long> ids) {
        dictionaryNodeMapper.changeNodeWithNewPath(bizLineId, dictionaryId, newParentPath, oldParentPath, ids);
    }

    @Override
    public List<DictionaryNodeEntity> listByParentPaths(Long bizLineId, Long dictionaryId,
                                                        Collection<String> parentPaths) {
        if (CollectionUtils.isEmpty(parentPaths)) {
            return Lists.newArrayList();
        }
        List<DictionaryNodePo> poList = dictionaryNodeMapper.listByParentPaths(bizLineId, dictionaryId, parentPaths);
        return DictionaryNodePo2EntityConverter.INSTANCE.toEntityList(poList);
    }
}
