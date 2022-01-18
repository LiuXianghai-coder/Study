package com.zwedu.rac.infrastructure.repository;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwedu.rac.domain.common.page.Pagination;
import com.zwedu.rac.domain.entity.StrategyEntity;
import com.zwedu.rac.domain.repository.StrategyRepository;
import com.zwedu.rac.infrastructure.converter.StrategyEntity2PoConverter;
import com.zwedu.rac.infrastructure.converter.StrategyPo2EntityConverter;
import com.zwedu.rac.infrastructure.mapper.StrategyMapper;
import com.zwedu.rac.infrastructure.po.StrategyPo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 存储类
 *
 * @author qingchuan
 * @date 2020/12/12
 */
@Repository
public class StrategyMybatisRepository implements StrategyRepository {
    @Resource
    private StrategyMapper strategyMapper;

    @Override
    public Pagination<StrategyEntity> listPage(Integer pageNo, Integer pageSize,
                                                 Long bizLineId, String searchVal) {
        try (Page page = PageHelper.startPage(pageNo, pageSize)) {
            List<StrategyPo> poList = strategyMapper.listPage(bizLineId, searchVal);
            List<StrategyEntity> entityList = StrategyPo2EntityConverter.INSTANCE.toEntityList(poList);
            Pagination<StrategyEntity> pagination =
                    new Pagination(pageNo, pageSize, page.getTotal());
            pagination.setDataList(entityList);
            return pagination;
        }
    }

    @Override
    public List<StrategyEntity> listByBizLineId(Long bizLineId) {
        List<StrategyPo> poList = strategyMapper.listByBizLineId(bizLineId);
        return StrategyPo2EntityConverter.INSTANCE.toEntityList(poList);
    }


    /**
     * 根据ID查询实体信息
     *
     * @param id ID
     * @return 实体信息
     */
    @Override
    public StrategyEntity queryById(Long id) {
        StrategyPo record = strategyMapper.selectByPrimaryKey(id);
        return StrategyPo2EntityConverter.INSTANCE.toEntity(record);
    }

    @Override
    public StrategyEntity queryById(Long bizLineId, Long id) {
        StrategyPo record = strategyMapper.queryById(bizLineId, id);
        return StrategyPo2EntityConverter.INSTANCE.toEntity(record);
    }

    @Override
    public Boolean hasSameEnName(Long bizLineId, String enName, Long id) {
        return strategyMapper.queryByEnName(bizLineId, enName, id) != null;
    }

    @Override
    public Boolean hasSameCnName(Long bizLineId, String cnName, Long id) {
        return strategyMapper.queryByCnName(bizLineId, cnName, id) != null;
    }

    /**
     * 更新访问策略
     *
     * @param record 访问策略实体
     */
    @Override
    public void insert(StrategyEntity record) {
        strategyMapper.insertSelective(StrategyEntity2PoConverter.INSTANCE.toPo(record));
    }

    /**
     * 更新访问策略
     *
     * @param record 访问策略实体
     */
    @Override
    public void edit(StrategyEntity record) {
        strategyMapper.updateByPrimaryKeySelective(StrategyEntity2PoConverter.INSTANCE.toPo(record));
    }


    /**
     * 更新访问策略
     *
     * @param record 访问策略实体
     */
    @Override
    public void delete(StrategyEntity record) {
        strategyMapper.updateByPrimaryKeySelective(StrategyEntity2PoConverter.INSTANCE.toPo(record));
    }

    @Override
    public List<StrategyEntity> listByIds(Long bizLineId, Collection<Long> ids) {
        return StrategyPo2EntityConverter.INSTANCE.toEntityList(strategyMapper.listByIds(bizLineId, ids));
    }
}
