package com.zwedu.rac.application.service;

import com.google.common.collect.Lists;
import com.zwedu.rac.application.converter.*;
import com.zwedu.rac.application.dto.ReqPaginationDto;
import com.zwedu.rac.application.dto.ResPaginationDto;
import com.zwedu.rac.application.dto.dimension.DimensionNodeSimpleDto;
import com.zwedu.rac.application.dto.ext.ExtDataComplexDto;
import com.zwedu.rac.application.dto.ext.ExtDataSimpleDto;
import com.zwedu.rac.application.dto.user.UserComplexDto;
import com.zwedu.rac.application.dto.user.UserPermitDto;
import com.zwedu.rac.application.dto.user.UserSimpleDto;
import com.zwedu.rac.domain.common.annotation.ReadAuth;
import com.zwedu.rac.domain.common.annotation.WriteAuth;
import com.zwedu.rac.domain.common.constant.SystemConstant;
import com.zwedu.rac.domain.common.enums.ExtPropertyTypeEnum;
import com.zwedu.rac.domain.common.util.Collections2;
import com.zwedu.rac.domain.common.validator.ParamAssert;
import com.zwedu.rac.domain.entity.DictionaryNodeEntity;
import com.zwedu.rac.domain.entity.ExtDataEntity;
import com.zwedu.rac.domain.entity.ExtPropertyEntity;
import com.zwedu.rac.domain.service.DictionaryNodeDomainService;
import com.zwedu.rac.domain.service.ExtPropertyDomainService;
import com.zwedu.rac.domain.service.UserDomainService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户应用层服务
 *
 * @author qingchuan
 * @date 2020/12/10
 */
@Service
public class UserAppService {
    @Resource
    private UserDomainService userDomainService;
    @Resource
    private ExtPropertyDomainService extPropertyDomainService;
    @Resource
    private DictionaryNodeDomainService dictionaryNodeDomainService;

    /**
     * 查询用户列表数据
     *
     * @param record 分页查询参数
     * @return 用户列表数据
     */
    @ReadAuth
    public ResPaginationDto<UserComplexDto> listPage(Long currentLoginId, ReqPaginationDto record) {
        return UserEntity2ComplexDtoConverter.INSTANCE.toPaginationDto(userDomainService
                .listPage(currentLoginId, record.getPageNo(), record.getPageSize(),
                        record.getBizLineId(), record.getSearchVal()));
    }

    /**
     * 查找用户授权的角色ID
     *
     * @param record        查询用户角色参数
     */
    public List<Long> listUserRoleId(UserPermitDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(record);
        return userDomainService.listUserRoleId(record.getUserId());
    }

    /**
     * 查找用户授权的维度节点
     *
     * @param record        查询用户授权维度节点参数
     */
    public List<DimensionNodeSimpleDto> listUserDimensionNodes(UserPermitDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(record);
        return DimensionNodeEntity2SimpleDtoConverter.INSTANCE.toDtoList(userDomainService
                .listUserDimensionNodes(record.getUserId()));
    }


    /**
     * 查找用户授权的扩展属性
     *
     * @param record        查询用户授权维度节点参数
     * @return
     */
    public List<ExtDataComplexDto> listUserExtProperty(UserPermitDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(record);
        List<ExtDataEntity> extDataEntityList = extPropertyDomainService
                .listExtProperty(record.getBizLineId(), SystemConstant.ENTITY_USER, record.getUserId());
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
     * 创建用户
     *
     * @param currentLoginId 登录用户ID
     * @param record        用户实体
     */
    @WriteAuth
    public void create(Long currentLoginId, UserSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        userDomainService.create(currentLoginId, UserSimpleDto2EntityConverter.INSTANCE.toEntity(record));
    }

    /**
     * 添加扩展属性
     *
     * @param currentLoginId 登录用户ID
     * @param record        记录数据
     */
    @WriteAuth
    public void addExtProperty(Long currentLoginId, ExtDataSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        extPropertyDomainService.addExtProperty(currentLoginId, ExtDataSimpleDto2EntityConverter.INSTANCE.toEntity(record));
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

    /**
     * 给用户授权
     *
     * @param currentLoginId 登录用户ID
     * @param record        用户权限记录
     */
    @WriteAuth
    public void grantRoles(Long currentLoginId, UserPermitDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentLoginId, record);
        userDomainService.bindRoles(currentLoginId, record.getUserId(), record.getRoleIds());
    }


    /**
     * 给用户授权
     *
     * @param currentLoginId 登录用户ID
     * @param record        用户权限记录
     */
    @WriteAuth
    public void grantDimensionNodes(Long currentLoginId, UserPermitDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentLoginId, record);
        userDomainService.bindDimensionNodes(currentLoginId, record.getUserId(), record.getDimensionNodeIds());
    }

    /**
     * 更新用户
     *
     * @param currentLoginId 登录用户ID
     * @param record        用户实体
     */
    @WriteAuth
    public void edit(Long currentLoginId, UserSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        userDomainService.edit(currentLoginId, UserSimpleDto2EntityConverter.INSTANCE.toEntity(record));
    }


    /**
     * 删除用户
     *
     * @param currentLoginId 登录用户ID
     * @param record        记录数据
     */
    @WriteAuth
    public void delete(Long currentLoginId, UserSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        userDomainService.delete(currentLoginId, record.getId());
    }

    /**
     * 查询用户信息
     *
     * @param enName 用户名
     * @return 用户信息
     */
    public UserSimpleDto queryByEnName(String enName) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(enName);
        return UserEntity2SimpleDtoConverter.INSTANCE.toDto(userDomainService.queryByEnName(enName));
    }
}
