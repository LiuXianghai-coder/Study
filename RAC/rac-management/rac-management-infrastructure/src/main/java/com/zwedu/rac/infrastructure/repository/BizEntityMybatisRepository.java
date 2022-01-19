package com.zwedu.rac.infrastructure.repository;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwedu.rac.domain.common.page.Pagination;
import com.zwedu.rac.domain.entity.BizEntity;
import com.zwedu.rac.domain.repository.BizEntityRepository;
import com.zwedu.rac.infrastructure.converter.BizEntity2PoConverter;
import com.zwedu.rac.infrastructure.converter.BizEntityPo2EntityConverter;
import com.zwedu.rac.infrastructure.mapper.BizEntityMapper;
import com.zwedu.rac.infrastructure.po.BizEntityPo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务实体存储类
 *
 * @author qingchuan
 * @date 2020/12/12
 */
@Repository
public class BizEntityMybatisRepository implements BizEntityRepository {
    @Resource
    private BizEntityMapper bizEntityMapper;

    @Override
    public Pagination<BizEntity> listPage(Integer pageNo, Integer pageSize,
                                          Long bizLineId, String searchVal) {
        try (Page page = PageHelper.startPage(pageNo, pageSize)) {
            List<BizEntityPo> poList = bizEntityMapper.listPage(bizLineId, searchVal);
            List<BizEntity> entityList = BizEntityPo2EntityConverter.INSTANCE.toEntityList(poList);
            Pagination<BizEntity> pagination =
                    new Pagination(pageNo, pageSize, page.getTotal());
            pagination.setDataList(entityList);
            return pagination;
        }
    }

    @Override
    public List<BizEntity> listByBizLineId(Long bizLineId) {
        List<BizEntityPo> poList = bizEntityMapper.listByBizLineId(bizLineId);
        return BizEntityPo2EntityConverter.INSTANCE.toEntityList(poList);
    }

    @Override
    public BizEntity queryById(Long bizLineId, Long id) {
        BizEntityPo record = bizEntityMapper.queryById(bizLineId, id);
        return BizEntityPo2EntityConverter.INSTANCE.toEntity(record);
    }

    @Override
    public BizEntity queryById(Long id) {
        BizEntityPo record = bizEntityMapper.selectByPrimaryKey(id);
        return BizEntityPo2EntityConverter.INSTANCE.toEntity(record);
    }

    @Override
    public BizEntity queryByEnName(Long bizLineId, String enName) {
        BizEntityPo record = bizEntityMapper.queryByEnName(bizLineId, enName, null);
        return BizEntityPo2EntityConverter.INSTANCE.toEntity(record);
    }

    @Override
    public Boolean hasSameEnName(Long bizLineId, String enName, Long id) {
        return bizEntityMapper.queryByEnName(bizLineId, enName, id) != null;
    }

    @Override
    public Boolean hasSameCnName(Long bizLineId, String cnName, Long id) {
        return bizEntityMapper.queryByCnName(bizLineId, cnName, id) != null;
    }

    /**
     * 更新业务实体
     *
     * @param record 业务实体实体
     */
    @Override
    public void insert(BizEntity record) {
        bizEntityMapper.insertSelective(BizEntity2PoConverter.INSTANCE.toPo(record));
    }

    /**
     * 更新业务实体
     *
     * @param record 业务实体实体
     */
    @Override
    public void edit(BizEntity record) {
        bizEntityMapper.updateByPrimaryKeySelective(BizEntity2PoConverter.INSTANCE.toPo(record));
    }


    /**
     * 更新业务实体
     *
     * @param record 业务实体实体
     */
    @Override
    public void delete(BizEntity record) {
        bizEntityMapper.updateByPrimaryKeySelective(BizEntity2PoConverter.INSTANCE.toPo(record));
    }
}
