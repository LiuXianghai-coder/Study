package com.zwedu.rac.application.service;

import com.google.common.collect.Lists;
import com.zwedu.rac.application.common.TreeBuilder;
import com.zwedu.rac.application.converter.DictionaryNodeEntity2ComplexDtoConverter;
import com.zwedu.rac.application.converter.DictionaryNodeSimpleDto2EntityConverter;
import com.zwedu.rac.application.converter.ExtDataEntity2ComplexDtoConverter;
import com.zwedu.rac.application.converter.ExtDataSimpleDto2EntityConverter;
import com.zwedu.rac.application.dto.dictionary.DictionaryNodeComplexDto;
import com.zwedu.rac.application.dto.dictionary.DictionaryNodeSimpleDto;
import com.zwedu.rac.application.dto.ext.ExtDataComplexDto;
import com.zwedu.rac.application.dto.ext.ExtDataSimpleDto;
import com.zwedu.rac.domain.common.annotation.ReadAuth;
import com.zwedu.rac.domain.common.annotation.WriteAuth;
import com.zwedu.rac.domain.common.enums.EntityPrefixEnum;
import com.zwedu.rac.domain.common.enums.ExtPropertyTypeEnum;
import com.zwedu.rac.domain.common.util.Collections2;
import com.zwedu.rac.domain.common.validator.ParamAssert;
import com.zwedu.rac.domain.entity.DictionaryEntity;
import com.zwedu.rac.domain.entity.DictionaryNodeEntity;
import com.zwedu.rac.domain.entity.ExtDataEntity;
import com.zwedu.rac.domain.entity.ExtPropertyEntity;
import com.zwedu.rac.domain.service.DictionaryDomainService;
import com.zwedu.rac.domain.service.DictionaryNodeDomainService;
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
 * 字典节点应用层服务
 *
 * @author qingchuan
 * @date 2020/12/10
 */
@Service
@Slf4j
public class DictionaryNodeAppService {
    @Resource
    private DictionaryDomainService dictionaryDomainService;
    @Resource
    private DictionaryNodeDomainService dictionaryNodeDomainService;
    @Resource
    private ExtPropertyDomainService extPropertyDomainService;


    /**
     * 查询授权的字典节点列表
     *
     * @param record 参数
     * @return 字典节点列表数据
     */
    @ReadAuth
    public List<DictionaryNodeComplexDto> listByParentId(DictionaryNodeSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        return DictionaryNodeEntity2ComplexDtoConverter.INSTANCE.toDtoList(dictionaryNodeDomainService
                .listByParentId(record.getBizLineId(), record.getDictionaryId(), record.getParentId()));
    }


    /**
     * 查询业务线对应的维度节点
     *
     * @param record 参数
     * @return 维度节点列表
     */
    @ReadAuth
    public List<DictionaryNodeComplexDto> listByDictionaryId(DictionaryNodeSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        List<DictionaryNodeEntity> dictionaryEntityList = dictionaryNodeDomainService
                .listByDictionaryId(record.getBizLineId(), record.getDictionaryId(), record.getSearchVal());
        List<DictionaryNodeComplexDto> dtoList = DictionaryNodeEntity2ComplexDtoConverter.INSTANCE
                .toDtoList(dictionaryEntityList);
        return TreeBuilder.buildTree(dtoList);
    }

    /**
     * 创建字典节点
     *
     * @param currentUserId 登录用户ID
     * @param record        字典节点实体
     */
    @WriteAuth
    public void create(Long currentUserId, DictionaryNodeSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        dictionaryNodeDomainService.create(currentUserId,
                DictionaryNodeSimpleDto2EntityConverter.INSTANCE.toEntity(record));
    }

    /**
     * 更新字典节点
     *
     * @param currentUserId 登录用户ID
     * @param record        字典节点实体
     */
    @WriteAuth
    public void edit(Long currentUserId, DictionaryNodeSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        dictionaryNodeDomainService.edit(currentUserId,
                DictionaryNodeSimpleDto2EntityConverter.INSTANCE.toEntity(record));
    }


    /**
     * 删除字典节点
     *
     * @param currentUserId 登录用户ID
     * @param record        记录数据
     */
    @WriteAuth
    public void delete(Long currentUserId, DictionaryNodeSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        dictionaryNodeDomainService.delete(currentUserId, record.getBizLineId(), record.getDictionaryId(), record.getId());
    }

    /**
     * 查找扩展属性
     *
     * @param record 查询用户授权维度节点参数
     * @return
     */
    public List<ExtDataComplexDto> listExtProperty(DictionaryNodeSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(record);
        DictionaryEntity dictionaryEntity = dictionaryDomainService.queryById(record.getBizLineId(),
                record.getDictionaryId());
        if (dictionaryEntity == null) {
            return Lists.newArrayList();
        }
        List<ExtDataEntity> extDataEntityList = extPropertyDomainService
                .listExtProperty(record.getBizLineId(), EntityPrefixEnum.DICTIONARY.join(dictionaryEntity.getEnName()),
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
