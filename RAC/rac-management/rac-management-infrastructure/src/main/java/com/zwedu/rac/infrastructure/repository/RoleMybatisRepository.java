package com.zwedu.rac.infrastructure.repository;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.zwedu.rac.domain.common.page.Pagination;
import com.zwedu.rac.domain.common.util.Collections2;
import com.zwedu.rac.domain.entity.RoleEntity;
import com.zwedu.rac.domain.repository.RoleRepository;
import com.zwedu.rac.infrastructure.converter.RoleEntity2PoConverter;
import com.zwedu.rac.infrastructure.converter.RolePo2EntityConverter;
import com.zwedu.rac.infrastructure.mapper.RoleFuncMapper;
import com.zwedu.rac.infrastructure.mapper.RoleMapper;
import com.zwedu.rac.infrastructure.mapper.RoleMenuMapper;
import com.zwedu.rac.infrastructure.po.RoleFuncPo;
import com.zwedu.rac.infrastructure.po.RoleMenuPo;
import com.zwedu.rac.infrastructure.po.RolePo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色存储类
 *
 * @author qingchuan
 * @date 2020/12/12
 */
@Repository
public class RoleMybatisRepository implements RoleRepository {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleFuncMapper roleFuncMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<RoleEntity> listByUserId(Long bizLineId, Long userId) {
        return RolePo2EntityConverter.INSTANCE.toEntityList(roleMapper
                .listByUserId(bizLineId, userId));
    }

    @Override
    public Pagination<RoleEntity> listPage(Integer pageNo, Integer pageSize,
                                           Long bizLineId, String searchVal) {
        try (Page page = PageHelper.startPage(pageNo, pageSize)) {
            List<RolePo> poList = roleMapper.listPage(bizLineId, searchVal);
            List<RoleEntity> entityList = RolePo2EntityConverter.INSTANCE.toEntityList(poList);
            Pagination<RoleEntity> pagination =
                    new Pagination(pageNo, pageSize, page.getTotal());
            pagination.setDataList(entityList);
            return pagination;
        }
    }

    @Override
    public List<RoleEntity> listByBizLineId(Long bizLineId) {
        return RolePo2EntityConverter.INSTANCE.toEntityList(roleMapper
                .listByBizLineId(bizLineId));
    }

    @Override
    public RoleEntity queryById(Long id) {
        RolePo record = roleMapper.selectByPrimaryKey(id);
        return RolePo2EntityConverter.INSTANCE.toEntity(record);
    }

    @Override
    public RoleEntity queryById(Long bizLineId, Long id) {
        RolePo record = roleMapper.queryById(bizLineId, id);
        return RolePo2EntityConverter.INSTANCE.toEntity(record);
    }

    @Override
    public Boolean hasSameEnName(Long bizLineId, String enName, Long id) {
        return roleMapper.queryByEnName(bizLineId, enName, id) != null;
    }

    @Override
    public Boolean hasSameCnName(Long bizLineId, String cnName, Long id) {
        return roleMapper.queryByCnName(bizLineId, cnName, id) != null;
    }

    /**
     * 更新角色
     *
     * @param record 角色实体
     */
    @Override
    public void insert(RoleEntity record) {
        roleMapper.insertSelective(RoleEntity2PoConverter.INSTANCE.toPo(record));
    }

    @Override
    public void bindAuth(Long currentUserId, Long roleId, Collection<Long> funcIds,
                          Long strategyId) {
        roleFuncMapper.unbindAuth(roleId, funcIds, strategyId);
        roleFuncMapper.bindAuth(currentUserId, roleId, funcIds, strategyId);
    }

    @Override
    public void bindMenu(Long currentUserId, Long roleId, Set<Long> menuIds) {
        roleMenuMapper.deleteByRoleId(roleId);
        roleMenuMapper.bindMenu(currentUserId, roleId, menuIds);
    }


    @Override
    public void unbindAuth(Long roleId, Collection<Long> funcIds,
                         Long strategyId) {
        roleFuncMapper.unbindAuth(roleId, funcIds, strategyId);
    }

    /**
     * 更新角色
     *
     * @param record 角色实体
     */
    @Override
    public void edit(RoleEntity record) {
        roleMapper.updateByPrimaryKeySelective(RoleEntity2PoConverter.INSTANCE.toPo(record));
    }


    /**
     * 更新角色
     *
     * @param record 角色实体
     */
    @Override
    public void delete(RoleEntity record) {
        roleMapper.updateByPrimaryKeySelective(RoleEntity2PoConverter.INSTANCE.toPo(record));
    }

    @Override
    public Pagination<Pair<Long, Long>> listAuth(Integer pageNo, Integer pageSize, Long bizLineId, Long roleId) {
        try (Page page = PageHelper.startPage(pageNo, pageSize)) {
            List<RoleFuncPo> poList = roleFuncMapper.listAuth(bizLineId, roleId);
            List<Pair<Long, Long>> retMap = CollectionUtils.isEmpty(poList) ? Lists.newArrayList() : poList.stream()
                    .map(input -> Pair.of(input.getFuncId(), input.getStrategyId())).collect(Collectors.toList());
            Pagination<Pair<Long, Long>> pagination =
                    new Pagination(pageNo, pageSize, page.getTotal());
            pagination.setDataList(retMap);
            return pagination;
        }
    }

    @Override
    public List<Long> listBindMenuId(Long bizLineId, Long roleId) {
        List<RoleMenuPo> roleMenuPoList = roleMenuMapper.listBindMenuId(bizLineId, roleId);
        return Collections2.toList(roleMenuPoList, input->input.getMenuId());
    }
}
