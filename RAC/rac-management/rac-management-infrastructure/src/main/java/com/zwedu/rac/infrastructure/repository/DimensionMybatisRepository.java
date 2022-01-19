package com.zwedu.rac.infrastructure.repository;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwedu.rac.domain.common.AuthInfoThreadLocal;
import com.zwedu.rac.domain.common.page.Pagination;
import com.zwedu.rac.domain.common.strategy.entity.AuthInfo;
import com.zwedu.rac.domain.entity.DimensionEntity;
import com.zwedu.rac.domain.repository.DimensionRepository;
import com.zwedu.rac.infrastructure.converter.DimensionEntity2PoConverter;
import com.zwedu.rac.infrastructure.converter.DimensionPo2EntityConverter;
import com.zwedu.rac.infrastructure.mapper.DimensionMapper;
import com.zwedu.rac.infrastructure.po.DimensionPo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 维度存储类
 *
 * @author qingchuan
 * @date 2020/12/12
 */
@Repository
public class DimensionMybatisRepository implements DimensionRepository {
    @Resource
    private DimensionMapper dimensionMapper;

    @Override
    public Pagination<DimensionEntity> listPage(Integer pageNo, Integer pageSize,
                                                Long bizLineId, String searchVal) {
        try (Page page = PageHelper.startPage(pageNo, pageSize)) {
            AuthInfo authInfo = AuthInfoThreadLocal.AUTH_INFO.get();
            List<DimensionPo> poList = dimensionMapper.listPage(bizLineId, searchVal, authInfo);
            List<DimensionEntity> entityList = DimensionPo2EntityConverter.INSTANCE.toEntityList(poList);
            Pagination<DimensionEntity> pagination =
                    new Pagination(pageNo, pageSize, page.getTotal());
            pagination.setDataList(entityList);
            return pagination;
        }
    }

    @Override
    public List<DimensionEntity> listByBizLineId(Long bizLineId) {
        List<DimensionPo> poList = dimensionMapper.listByBizLineId(bizLineId, AuthInfoThreadLocal.AUTH_INFO.get());
        return DimensionPo2EntityConverter.INSTANCE.toEntityList(poList);
    }


    /**
     * 根据ID查询实体信息
     *
     * @param id ID
     * @return 实体信息
     */
    @Override
    public DimensionEntity queryById(Long id) {
        DimensionPo record = dimensionMapper.selectByPrimaryKey(id);
        return DimensionPo2EntityConverter.INSTANCE.toEntity(record);
    }

    @Override
    public DimensionEntity queryById(Long bizLineId, Long id) {
        DimensionPo record = dimensionMapper.queryById(bizLineId, id);
        return DimensionPo2EntityConverter.INSTANCE.toEntity(record);
    }

    @Override
    public Boolean hasSameEnName(Long bizLineId, String enName, Long id) {
        return dimensionMapper.queryByEnName(bizLineId, enName, id) != null;
    }

    @Override
    public DimensionEntity queryByEnName(Long bizLineId, String enName) {
        DimensionPo record = dimensionMapper.queryByEnName(bizLineId, enName, null);
        return DimensionPo2EntityConverter.INSTANCE.toEntity(record);
    }

    @Override
    public Boolean hasSameCnName(Long bizLineId, String cnName, Long id) {
        return dimensionMapper.queryByCnName(bizLineId, cnName, id) != null;
    }

    /**
     * 更新维度
     *
     * @param record 维度实体
     */
    @Override
    public void insert(DimensionEntity record) {
        dimensionMapper.insertSelective(DimensionEntity2PoConverter.INSTANCE.toPo(record));
    }

    /**
     * 更新维度
     *
     * @param record 维度实体
     */
    @Override
    public void edit(DimensionEntity record) {
        dimensionMapper.updateByPrimaryKeySelective(DimensionEntity2PoConverter.INSTANCE.toPo(record));
    }


    /**
     * 更新维度
     *
     * @param record 维度实体
     */
    @Override
    public void delete(DimensionEntity record) {
        dimensionMapper.updateByPrimaryKeySelective(DimensionEntity2PoConverter.INSTANCE.toPo(record));
    }
}
