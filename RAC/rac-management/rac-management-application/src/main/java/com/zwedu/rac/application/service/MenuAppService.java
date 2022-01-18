package com.zwedu.rac.application.service;

import com.zwedu.rac.application.common.TreeBuilder;
import com.zwedu.rac.application.converter.MenuEntity2ComplexDtoConverter;
import com.zwedu.rac.application.converter.MenuSimpleDto2EntityConverter;
import com.zwedu.rac.application.dto.menu.MenuComplexDto;
import com.zwedu.rac.application.dto.menu.MenuSimpleDto;
import com.zwedu.rac.domain.common.annotation.WriteAuth;
import com.zwedu.rac.domain.common.validator.ParamAssert;
import com.zwedu.rac.domain.entity.MenuEntity;
import com.zwedu.rac.domain.service.MenuDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单应用层服务
 *
 * @author qingchuan
 * @date 2020/12/10
 */
@Service
@Slf4j
public class MenuAppService {
    @Resource
    private MenuDomainService menuDomainService;


    /**
     * 查询子菜单
     *
     * @param record 查询参数
     * @return 菜单列表数据
     */
    public List<MenuComplexDto> listByParentId(MenuSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        // 查询对应的菜单列表
        List<MenuEntity> MenuEntityList = menuDomainService.
                listByParentId(record.getBizLineId(), record.getParentId());
        return MenuEntity2ComplexDtoConverter.INSTANCE.toDtoList(MenuEntityList);
    }

    /**
     * 查询菜单
     *
     * @param record 参数
     * @return 列表数据
     */
    public List<MenuComplexDto> listByBizLineId(MenuSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(record);
        // 查询对应的功能列表
        List<MenuEntity> menuEntityList = menuDomainService.listByBizLineId(record.getBizLineId(),
                record.getSearchVal());
        List<MenuComplexDto> funcComplexDtoList = MenuEntity2ComplexDtoConverter.INSTANCE.toDtoList(menuEntityList);
        return TreeBuilder.buildTree(funcComplexDtoList);
    }

    /**
     * 创建菜单
     *
     * @param currentUserId 登录用户ID
     * @param record        菜单实体
     */
    @WriteAuth
    public void create(Long currentUserId, MenuSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        menuDomainService.create(currentUserId, MenuSimpleDto2EntityConverter.INSTANCE.toEntity(record));
    }

    /**
     * 更新菜单
     *
     * @param currentUserId 登录用户ID
     * @param record        菜单实体
     */
    @WriteAuth
    public void edit(Long currentUserId, MenuSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        menuDomainService.edit(currentUserId, MenuSimpleDto2EntityConverter.INSTANCE.toEntity(record));
    }


    /**
     * 删除菜单
     *
     * @param currentUserId 登录用户ID
     * @param record        记录数据
     */
    @WriteAuth
    public void delete(Long currentUserId, MenuSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        menuDomainService.delete(currentUserId, record.getBizLineId(), record.getId());
    }

}
