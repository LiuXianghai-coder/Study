package com.zwedu.rac.application.service;

import com.zwedu.rac.application.converter.FuncEntity2ComplexDtoConverter;
import com.zwedu.rac.application.converter.RoleEntity2ComplexDtoConverter;
import com.zwedu.rac.application.converter.RoleEntity2SimpleDtoConverter;
import com.zwedu.rac.application.converter.RoleSimpleDto2EntityConverter;
import com.zwedu.rac.application.dto.ReqPaginationDto;
import com.zwedu.rac.application.dto.ResPaginationDto;
import com.zwedu.rac.application.dto.role.FuncStrategyComplexDto;
import com.zwedu.rac.application.dto.role.RoleComplexDto;
import com.zwedu.rac.application.dto.role.RoleSimpleDto;
import com.zwedu.rac.domain.common.annotation.WriteAuth;
import com.zwedu.rac.domain.common.page.Pagination;
import com.zwedu.rac.domain.common.util.Collections2;
import com.zwedu.rac.domain.common.validator.ParamAssert;
import com.zwedu.rac.domain.entity.FuncEntity;
import com.zwedu.rac.domain.entity.RoleEntity;
import com.zwedu.rac.domain.entity.StrategyEntity;
import com.zwedu.rac.domain.service.FuncDomainService;
import com.zwedu.rac.domain.service.RoleDomainService;
import com.zwedu.rac.domain.service.StrategyDomainService;
import com.zwedu.rac.domain.service.UserDomainService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 角色应用层服务
 *
 * @author qingchuan
 * @date 2020/12/10
 */
@Service
@Slf4j
public class RoleAppService {
    @Resource
    private RoleDomainService roleDomainService;
    @Resource
    private UserDomainService userService;
    @Resource
    private FuncDomainService funcDomainService;
    @Resource
    private StrategyDomainService strategyDomainService;

    /**
     * 查询角色列表数据
     *
     * @param record 分页查询参数
     * @return 角色列表数据
     */
    public ResPaginationDto<RoleComplexDto> listPage(ReqPaginationDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        // 查询对应的角色列表
        Pagination<RoleEntity> roleEntityList = roleDomainService
                .listPage(record.getPageNo(), record.getPageSize(),
                        record.getBizLineId(), record.getSearchVal());
        return RoleEntity2ComplexDtoConverter.INSTANCE.toPaginationDto(roleEntityList);
    }

    /**
     * 查找授权角色列表
     *
     * @return 授权角色列表
     */
    public List<RoleSimpleDto> listByBizLineId(RoleSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        List<RoleEntity> roleEntityList = roleDomainService.listByBizLineId(record.getBizLineId());
        return RoleEntity2SimpleDtoConverter.INSTANCE.toDtoList(roleEntityList);
    }

    /**
     * 查找角色功能ID
     *
     * @param record 查询角色功能参数
     */
    public Pagination<FuncStrategyComplexDto> listAuth(ReqPaginationDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(record);
        Pagination<Pair<Long, Long>> pagination = roleDomainService.listAuth(record.getPageNo(),
                record.getPageSize(), record.getBizLineId(), record.getId());
        Collection<Long> funcIds = Collections2.toSet(pagination.getDataList(), input -> input.getKey());
        Collection<Long> strategyIds = Collections2.toSet(pagination.getDataList(), input -> input.getValue());
        Map<Long, FuncEntity> funcMap = funcDomainService.listByIds(record.getBizLineId(), funcIds);
        Map<Long, StrategyEntity> strategyMap = strategyDomainService.listByIds(record.getBizLineId(), strategyIds);
        return FuncEntity2ComplexDtoConverter.INSTANCE.toPaginationDto(pagination, funcMap, strategyMap);
    }


    /**
     * 已绑定菜单ID
     *
     * @param record 记录
     * @return 列表
     */
    public List<Long> listBindMenuId(RoleSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        return roleDomainService.listBindMenuId(record.getBizLineId(), record.getRoleId());
    }

    /**
     * 创建角色
     *
     * @param currentUserId 登录用户ID
     * @param record        角色实体
     */
    @WriteAuth
    public void create(Long currentUserId, RoleSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        roleDomainService.create(currentUserId, RoleSimpleDto2EntityConverter.INSTANCE.toEntity(record));
    }


    /**
     * 绑定角色功能
     *
     * @param currentUserId 登录用户ID
     * @param record        角色功能记录
     */
    @WriteAuth
    public void bindAuth(Long currentUserId, RoleSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, record);
        roleDomainService.bindAuth(currentUserId, record.getBizLineId(), record.getRoleId(), record.getFuncIds(),
                record.getStrategyId());
    }

    /**
     * 绑定角色菜单
     *
     * @param currentUserId 登录用户ID
     * @param record        角色功能记录
     */
    @WriteAuth
    public void bindMenu(Long currentUserId, RoleSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, record);
        roleDomainService.bindMenu(currentUserId, record.getBizLineId(), record.getRoleId(), record.getMenuIds());
    }

    /**
     * 解绑角色功能
     *
     * @param record 角色功能记录
     */
    @WriteAuth
    public void unbindAuth(RoleSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(record);
        roleDomainService.unbindAuth(record.getBizLineId(), record.getRoleId(), record.getFuncIds(),
                record.getStrategyId());
    }

    /**
     * 更新角色
     *
     * @param currentUserId 登录用户ID
     * @param record        角色实体
     */
    @WriteAuth
    public void edit(Long currentUserId, RoleSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        roleDomainService.edit(currentUserId, RoleSimpleDto2EntityConverter.INSTANCE.toEntity(record));
    }


    /**
     * 删除角色
     *
     * @param currentUserId 登录用户ID
     * @param record        记录数据
     */
    @WriteAuth
    public void delete(Long currentUserId, RoleSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        roleDomainService.delete(currentUserId, record.getBizLineId(), record.getId());
    }
}
