package com.zwedu.rac.infrastructure.repository;

import com.google.common.collect.Lists;
import com.zwedu.rac.domain.common.util.Collections2;
import com.zwedu.rac.domain.entity.MenuEntity;
import com.zwedu.rac.domain.repository.MenuRepository;
import com.zwedu.rac.infrastructure.converter.MenuEntity2PoConverter;
import com.zwedu.rac.infrastructure.converter.MenuPo2EntityConverter;
import com.zwedu.rac.infrastructure.mapper.MenuMapper;
import com.zwedu.rac.infrastructure.po.IdNumPo;
import com.zwedu.rac.infrastructure.po.MenuPo;
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
 * 菜单存储类
 *
 * @author qingchuan
 * @date 2020/12/10
 */
@Repository
public class MenuMybatisRepository implements MenuRepository {
    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<MenuEntity> listByRoleIds(Long bizLineId, Collection<Long> roleIds) {
        List<MenuPo> poList = menuMapper.listByRoleIds(bizLineId, roleIds);
        return MenuPo2EntityConverter.INSTANCE.toEntityList(poList);
    }

    @Override
    public List<MenuEntity> listByParentId(Long bizLineId, Long parentId) {
        List<MenuPo> poList = menuMapper.listByParentId(bizLineId, parentId);
        if (CollectionUtils.isEmpty(poList)) {
            return Lists.newArrayList();
        }
        Set<Long> idSet = poList.stream().map(input -> input.getId()).collect(Collectors.toSet());
        List<IdNumPo> childCountList = menuMapper.countChildByIds(bizLineId, idSet);
        Map<Long, Long> id2ChildCountMap = Collections2.toMap(childCountList, input -> input.getId(),
                input -> input.getChildCount());
        return MenuPo2EntityConverter.INSTANCE.toEntityList(poList, id2ChildCountMap);
    }


    @Override
    public List<MenuEntity> listByBizLineId(Long bizLineId, String searchVal) {
        List<MenuPo> poList = menuMapper.listByBizLineId(bizLineId, searchVal);
        List<MenuEntity> retList = MenuPo2EntityConverter.INSTANCE.toEntityList(poList);
        if (StringUtils.isNotEmpty(searchVal) && CollectionUtils.isNotEmpty(retList)) {
            // 如果检索值不为空，并且查询出结果，则按照节点path查找子节点
            List<MenuPo> childList = menuMapper.listByParentPaths(bizLineId,
                    Collections2.toList(retList, input -> input.getPath()));
            retList.addAll(MenuPo2EntityConverter.INSTANCE.toEntityList(childList));
        }
        return retList;
    }

    @Override
    public List<MenuEntity> listByIds(Long bizLineId, Collection<Long> menuIds) {
        List<MenuPo> poList = menuMapper.listByIds(bizLineId, menuIds);
        return MenuPo2EntityConverter.INSTANCE.toEntityList(poList);
    }

    @Override
    public MenuEntity queryById(Long id) {
        MenuPo record = menuMapper.selectByPrimaryKey(id);
        return MenuPo2EntityConverter.INSTANCE.toEntity(record);
    }

    @Override
    public MenuEntity queryById(Long bizLineId, Long id) {
        MenuPo record = menuMapper.queryById(bizLineId, id);
        return MenuPo2EntityConverter.INSTANCE.toEntity(record);
    }

    @Override
    public Boolean hasSameEnName(Long bizLineId, String enName, Long id) {
        return menuMapper.queryByEnName(bizLineId, enName, id) != null;
    }

    @Override
    public Boolean hasSameCnName(Long bizLineId, String cnName, Long id) {
        return menuMapper.queryByCnName(bizLineId, cnName, id) != null;
    }

    @Override
    public void insert(MenuEntity record) {
        menuMapper.insertSelective(MenuEntity2PoConverter.INSTANCE.toPo(record));
    }

    /**
     * 更新菜单
     *
     * @param record 菜单实体
     */
    @Override
    public void edit(MenuEntity record) {
        menuMapper.updateByPrimaryKeySelective(MenuEntity2PoConverter.INSTANCE.toPo(record));
    }


    /**
     * 删除菜单
     *
     * @param record 菜单实体
     */
    @Override
    public void delete(MenuEntity record) {
        menuMapper.updateByPrimaryKeySelective(MenuEntity2PoConverter.INSTANCE.toPo(record));
    }

    @Override
    public List<MenuEntity> listByParentPaths(Long bizLineId, Collection<String> parentPaths) {
        List<MenuPo> poList = menuMapper.listByParentPaths(bizLineId, parentPaths);
        return MenuPo2EntityConverter.INSTANCE.toEntityList(poList);
    }

    @Override
    public void changeNodeWithNewPath(Long bizLineId, String newParentPath, String oldParentPath, Set<Long> ids) {
        menuMapper.changeNodeWithNewPath(bizLineId, newParentPath, oldParentPath, ids);
    }

}
