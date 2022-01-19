package com.zwedu.rac.application.service;

import com.google.common.collect.Lists;
import com.zwedu.rac.application.common.TreeBuilder;
import com.zwedu.rac.application.converter.*;
import com.zwedu.rac.domain.common.annotation.NoneAuth;
import com.zwedu.rac.domain.common.util.Collections2;
import com.zwedu.rac.domain.common.validator.ParamAssert;
import com.zwedu.rac.domain.entity.*;
import com.zwedu.rac.domain.service.*;
import com.zwedu.rac.sdk.rdo.*;
import com.zwedu.rac.sdk.rpo.DimensionAuthRpo;
import com.zwedu.rac.sdk.rpo.FuncAuthRpo;
import com.zwedu.rac.sdk.rpo.MenuRpo;
import com.zwedu.rac.sdk.rpo.UserExtPropertyRpo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.zwedu.rac.domain.common.constant.SystemConstant.ENTITY_USER;

/**
 * 功能权限应用层实现
 *
 * @author qingchuan
 * @date 2020/12/11
 */
@Slf4j
@Service
@NoneAuth
public class AuthAppService {

    @Resource
    private RoleDomainService roleDomainService;
    @Resource
    private FuncDomainService funcDomainService;
    @Resource
    private MenuDomainService menuDomainService;
    @Resource
    private DimensionDomainService dimensionDomainService;
    @Resource
    private DimensionNodeDomainService dimensionNodeDomainService;
    @Resource
    private UserDomainService userDomainService;
    @Resource
    private ExtPropertyDomainService extPropertyDomainService;
    @Resource
    private BizEntityDomainService bizEntityDomainService;
    @Resource
    private BizLineDomainService bizLineDomainService;


    /**
     * 查询功能授权URL列表数据
     *
     * @param rpo 请求参数
     * @return 功能URL
     */
    public List<FuncRdo> listFuncAuth(FuncAuthRpo rpo) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(rpo);
        // 获取角色列表
        List<RoleEntity> roleList = roleDomainService.listByUserId(rpo.getBizLineId(), rpo.getUserId());
        // 获取角色对应的功能
        List<FuncEntity> funcList = funcDomainService.listByRoleIds(rpo.getBizLineId(),
                roleList.stream().map(input -> input.getId()).collect(Collectors.toSet()));
        if (CollectionUtils.isNotEmpty(funcList)) {
            // 获取功能对应的所有子节点
            List<FuncEntity> childFuncList = funcDomainService.listByParentPaths(rpo.getBizLineId(),
                    funcList.stream().map(input -> input.getPath()).collect(Collectors.toSet()));
            funcList.addAll(extendStrategy(funcList, Collections2.trimNull(childFuncList)));
        }
        return FuncEntity2RdoConverter.INSTANCE.toRdoList(funcList);
    }

    /**
     * 子节点继承父节点策略
     *
     * @param funcList 功能列表
     * @return 功能列表
     */
    public List<FuncEntity> extendStrategy(List<FuncEntity> funcList, Collection<FuncEntity> childList) {
        if (CollectionUtils.isEmpty(childList)) {
            return Lists.newArrayList();
        }
        // 获取排序的列表，保证层级顺序
        List<FuncEntity> sortedList = childList.stream().sorted(Comparator.comparing(FuncEntity::getLevel))
                .collect(Collectors.toList());
        Map<Long, StrategyEntity> funcStrategyMap = Collections2.toMap(funcList, input -> input.getId(),
                input -> input.getStrategyEntity());
        for (FuncEntity node : sortedList) {
            node.setStrategyEntity(funcStrategyMap.get(node.getParentId()));
            funcStrategyMap.put(node.getId(), funcStrategyMap.get(node.getParentId()));
        }
        return sortedList;
    }

    /**
     * 查询菜单树
     *
     * @param rpo 查询参数
     * @return 菜单
     */
    public List<MenuRdo> listMenu(MenuRpo rpo) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(rpo);
        // 获取角色列表
        List<RoleEntity> roleList = roleDomainService.listByUserId(rpo.getBizLineId(), rpo.getUserId());
        if (CollectionUtils.isEmpty(roleList)) {
            return Lists.newArrayList();
        }
        // 获取角色对应的菜单
        List<MenuEntity> menuList = menuDomainService.listByRoleIds(rpo.getBizLineId(),
                roleList.stream().map(input -> input.getId()).collect(Collectors.toSet()));
        // 获取菜单对应的父节点
        if (CollectionUtils.isEmpty(menuList)) {
            return Lists.newArrayList();
        }
        List<MenuEntity> parentMenuList = menuDomainService.listByIds(rpo.getBizLineId(), menuList.stream()
                .map(input -> input.getParentId()).collect(Collectors.toSet()));
        if (CollectionUtils.isNotEmpty(parentMenuList)) {
            menuList.addAll(parentMenuList);
        }
        List<MenuEntity> sortedList = menuList.stream().sorted(Comparator.comparing(MenuEntity::getSeq))
                .collect(Collectors.toList());
        List<MenuRdo> rdoList = MenuEntity2RdoConverter.INSTANCE.toRdoList(sortedList);
        return TreeBuilder.buildTree(rdoList);
    }

    /**
     * 查询维度节点
     *
     * @param dimensionNodeId 维度节点ID
     * @return 维度节点列表
     */
    public DimensionNodeRdo queryDimensionNodeById(Long dimensionNodeId) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(dimensionNodeId);
        return DimensionNodeEntity2RdoConverter.INSTANCE.toRdo(dimensionNodeDomainService
                .queryById(dimensionNodeId));
    }

    /**
     * 查询维度节点
     *
     * @return 维度节点列表
     */
    public List<DimensionNodeRdo> listDimensionNodeByIds(List<Long> dimensionNodeIds) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(dimensionNodeIds);
        return DimensionNodeEntity2RdoConverter.INSTANCE.toRdoList(dimensionNodeDomainService
                .listDimensionNodeByIds(dimensionNodeIds));
    }

    /**
     * 查询用户绑定的指定业务线和英文名下维度节点
     *
     * @param rpo 请求查询参数
     * @return 维度节点列表
     */
    public List<DimensionNodeRdo> listDimensionNodeByUser(DimensionAuthRpo rpo) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(rpo);
        DimensionEntity dimensionEntity = dimensionDomainService
                .queryByEnName(rpo.getBizLineId(), rpo.getDimensionEnName());
        if (dimensionEntity == null) {
            return Lists.newArrayList();
        }
        List<DimensionNodeEntity> dimensionNodeEntityList = userDomainService
                .listUserDimensionNodes(rpo.getUserId(), rpo.getBizLineId(), dimensionEntity.getId());
        return DimensionNodeEntity2RdoConverter.INSTANCE.toRdoList(dimensionNodeEntityList);
    }

    /**
     * 查询用户绑定的指定业务线和英文名下维度节点和子节点
     *
     * @param rpo 请求查询参数
     * @return 维度节点列表
     */
    public List<DimensionNodeRdo> listDimensionNodeWithChildByUser(DimensionAuthRpo rpo) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(rpo);
        DimensionEntity dimensionEntity = dimensionDomainService
                .queryByEnName(rpo.getBizLineId(), rpo.getDimensionEnName());
        if (dimensionEntity == null) {
            return Lists.newArrayList();
        }
        List<DimensionNodeEntity> dimensionNodeEntityList = userDomainService
                .listUserDimensionNodes(rpo.getUserId(), rpo.getBizLineId(), dimensionEntity.getId());
        if (CollectionUtils.isEmpty(dimensionNodeEntityList)) {
            return Lists.newArrayList();
        }
        List<DimensionNodeEntity> childDimensionNodeList = dimensionNodeDomainService
                .listByParentPaths(rpo.getBizLineId(), dimensionEntity.getId(),
                        dimensionNodeEntityList.stream().map(input -> input.getPath()).collect(Collectors.toSet()));
        dimensionNodeEntityList.addAll(Collections2.trimNull(childDimensionNodeList));
        return DimensionNodeEntity2RdoConverter.INSTANCE.toRdoList(dimensionNodeEntityList);
    }

    /**
     * 查询用户扩展属性
     *
     * @param rpo 扩展属性
     * @return 扩展属性
     */
    public List<ExtPropertyRdo> listExtPropertyByUser(UserExtPropertyRpo rpo) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(rpo);
        BizEntity bizEntity = bizEntityDomainService.queryByEnName(rpo.getBizLineId(), ENTITY_USER);
        if (bizEntity == null) {
            return Lists.newArrayList();
        }
        List<ExtDataEntity> extDataList = extPropertyDomainService
                .listDataByBizId(bizEntity.getId(), rpo.getUserId());
        return ExtDataEntity2RdoConverter.INSTANCE.toRdoList(extDataList);
    }

    /**
     * 根据用户ID获取用户信息
     *
     * @param bizLineId 业务线ID
     * @param userId    用户ID
     * @return 用户信息
     */
    @Cacheable
    public UserRdo queryUser(Long bizLineId, Long userId) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(bizLineId, userId);
        UserEntity userEntity = userDomainService.queryById(userId);
        // 获取用户扩展属性
        List<ExtPropertyRdo> extPropertyRdoList = listExtPropertyByUser(new UserExtPropertyRpo(bizLineId, userId));
        return UserEntity2RdoConverter.INSTANCE.toRdo(userEntity, extPropertyRdoList);
    }
}
