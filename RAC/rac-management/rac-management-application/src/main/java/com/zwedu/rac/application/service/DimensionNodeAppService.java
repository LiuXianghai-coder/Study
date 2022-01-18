package com.zwedu.rac.application.service;

import com.google.common.collect.Lists;
import com.zwedu.rac.application.common.TreeBuilder;
import com.zwedu.rac.application.converter.DimensionNodeEntity2ComplexDtoConverter;
import com.zwedu.rac.application.converter.DimensionNodeSimpleDto2EntityConverter;
import com.zwedu.rac.application.converter.ExtDataEntity2ComplexDtoConverter;
import com.zwedu.rac.application.converter.ExtDataSimpleDto2EntityConverter;
import com.zwedu.rac.application.dto.dimension.DimensionNodeComplexDto;
import com.zwedu.rac.application.dto.dimension.DimensionNodeSimpleDto;
import com.zwedu.rac.application.dto.ext.ExtDataComplexDto;
import com.zwedu.rac.application.dto.ext.ExtDataSimpleDto;
import com.zwedu.rac.domain.common.annotation.WriteAuth;
import com.zwedu.rac.domain.common.enums.EntityPrefixEnum;
import com.zwedu.rac.domain.common.enums.ExtPropertyTypeEnum;
import com.zwedu.rac.domain.common.util.Collections2;
import com.zwedu.rac.domain.common.validator.ParamAssert;
import com.zwedu.rac.domain.entity.*;
import com.zwedu.rac.domain.service.DictionaryNodeDomainService;
import com.zwedu.rac.domain.service.DimensionDomainService;
import com.zwedu.rac.domain.service.DimensionNodeDomainService;
import com.zwedu.rac.domain.service.ExtPropertyDomainService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 维度节点节点应用层服务
 *
 * @author qingchuan
 * @date 2020/12/10
 */
@Service
@Slf4j
public class DimensionNodeAppService {
    @Resource
    private DimensionNodeDomainService dimensionNodeDomainService;
    @Resource
    private DimensionDomainService dimensionDomainService;
    @Resource
    private ExtPropertyDomainService extPropertyDomainService;
    @Resource
    private DictionaryNodeDomainService dictionaryNodeDomainService;


    /**
     * 查询授权的维度节点节点列表
     *
     * @return 维度节点节点列表数据
     */
    public List<DimensionNodeComplexDto> listByParentId(DimensionNodeSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        return DimensionNodeEntity2ComplexDtoConverter.INSTANCE.toDtoList(dimensionNodeDomainService
                .listByParentId(record.getBizLineId(), record.getDimensionId(), record.getParentId()));
    }


    /**
     * 查询菜单
     *
     * @param record 参数
     * @return 列表数据
     */
    public List<DimensionNodeComplexDto> listByDimensionId(DimensionNodeSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(record);
        // 查询对应的功能列表
        List<DimensionNodeEntity> dimensionNodeEntityList = dimensionNodeDomainService
                .listByDimensionId(record.getBizLineId(), record.getDimensionId(), record.getSearchVal());
        List<DimensionNodeComplexDto> funcComplexDtoList = DimensionNodeEntity2ComplexDtoConverter.INSTANCE
                .toDtoList(dimensionNodeEntityList);
        return TreeBuilder.buildTree(funcComplexDtoList);
    }

    /**
     * 创建维度节点节点
     *
     * @param currentUserId 登录用户ID
     * @param record        维度节点节点实体
     */
    @WriteAuth
    public void create(Long currentUserId, DimensionNodeSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        dimensionNodeDomainService.create(currentUserId, DimensionNodeSimpleDto2EntityConverter.INSTANCE.toEntity(record));
    }

    /**
     * 更新维度节点节点
     *
     * @param currentUserId 登录用户ID
     * @param record        维度节点节点实体
     */
    @WriteAuth
    public void edit(Long currentUserId, DimensionNodeSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        dimensionNodeDomainService.edit(currentUserId, DimensionNodeSimpleDto2EntityConverter.INSTANCE.toEntity(record));
    }


    /**
     * 删除维度节点节点
     *
     * @param currentUserId 登录用户ID
     * @param record        记录数据
     */
    @WriteAuth
    public void delete(Long currentUserId, DimensionNodeSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        dimensionNodeDomainService.delete(currentUserId, record.getBizLineId(), record.getDimensionId(), record.getId());
    }

    /**
     * 绑定主体节点操作客体节点
     *
     * @param currentUserId 登录用户ID
     * @param record        维度节点节点实体
     */
    @WriteAuth
    public void bindObjectNode(Long currentUserId, DimensionNodeSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(currentUserId, record);
        dimensionNodeDomainService.bindObjectNode(currentUserId, record.getBizLineId(), record.getDimensionId(),
                record.getId(), record.getObjectNodeId());
    }

    /**
     * 解绑主体节点操作客体节点
     *
     * @param currentUserId 登录用户ID
     * @param record        维度节点节点实体
     */
    @WriteAuth
    public void unbindObjectNode(Long currentUserId, DimensionNodeSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(currentUserId, record);
        dimensionNodeDomainService.unbindObjectNode(currentUserId, record.getBizLineId(), record.getDimensionId(),
                record.getId(), record.getObjectNodeId());
    }

    /**
     * 查询客体节点
     *
     * @param record 记录
     * @return 客体节点
     */
    public DimensionNodeComplexDto queryObjectNode(DimensionNodeSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        Long objectNodeId = dimensionNodeDomainService.queryObjectNodeId(record.getBizLineId(), record.getDimensionId(),
                record.getId());
        if (objectNodeId == null) {
            return null;
        }
        return DimensionNodeEntity2ComplexDtoConverter.INSTANCE.toDto(dimensionNodeDomainService
                .queryById(record.getBizLineId(), record.getDimensionId(), objectNodeId));
    }

    /**
     * 查找扩展属性
     *
     * @param record 查询维度节点扩展属性
     * @return 维度扩展属性列表
     */
    public List<ExtDataComplexDto> listExtProperty(DimensionNodeSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(record);
        DimensionEntity dimensionEntity = dimensionDomainService
                .queryById(record.getBizLineId(), record.getDimensionId());
        if (dimensionEntity == null) {
            return Lists.newArrayList();
        }
        List<ExtDataEntity> extDataEntityList = extPropertyDomainService
                .listExtProperty(record.getBizLineId(), EntityPrefixEnum.DIMENSION.join(dimensionEntity.getEnName()),
                        record.getId());
        if (CollectionUtils.isEmpty(extDataEntityList)) {
            return Lists.newArrayList();
        }
        // 获取扩展属性ID
        List<Long> extPropertyIds = extDataEntityList.stream()
                .map(input -> input.getExtPropertyId()).collect(Collectors.toList());
        List<ExtPropertyEntity> extPropertyEntityList = extPropertyDomainService.listByIds(extPropertyIds);
        if (CollectionUtils.isEmpty(extPropertyEntityList)) {
            return Lists.newArrayList();
        }
        Map<Long, ExtPropertyEntity> extPropertyMap = Collections2.toMap(extPropertyEntityList, input -> input.getId(),
                input -> input);
        Set<Long> dictionaryIds = extPropertyEntityList.stream()
                .filter(input -> input.getType().equals(ExtPropertyTypeEnum.ENUM.getValue()))
                .map(input -> input.getDictionaryId())
                .collect(Collectors.toSet());
        Map<Long, Map<Integer, DictionaryNodeEntity>> dictionaryNodeMap =
                dictionaryNodeDomainService.listByDictionaryIds(dictionaryIds);
        return ExtDataEntity2ComplexDtoConverter.INSTANCE.toDtoList(extDataEntityList, extPropertyMap,
                dictionaryNodeMap);
    }


    /**
     * 添加扩展属性
     *
     * @param currentUserId 登录用户ID
     * @param record        记录数据
     */
    @WriteAuth
    public void addExtProperty(Long currentUserId, ExtDataSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        extPropertyDomainService.addExtProperty(currentUserId,
                ExtDataSimpleDto2EntityConverter.INSTANCE.toEntity(record));
    }

    /**
     * 删除扩展属性
     *
     * @param record 记录数据
     */
    @WriteAuth
    public void dropExtProperty(ExtDataSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        extPropertyDomainService.dropExtProperty(ExtDataSimpleDto2EntityConverter.INSTANCE.toEntity(record));
    }
}
