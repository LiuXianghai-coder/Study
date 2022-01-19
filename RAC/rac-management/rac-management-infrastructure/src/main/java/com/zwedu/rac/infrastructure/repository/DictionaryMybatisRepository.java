package com.zwedu.rac.infrastructure.repository;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwedu.rac.domain.common.page.Pagination;
import com.zwedu.rac.domain.entity.DictionaryEntity;
import com.zwedu.rac.domain.repository.DictionaryRepository;
import com.zwedu.rac.infrastructure.converter.DictionaryEntity2PoConverter;
import com.zwedu.rac.infrastructure.converter.DictionaryPo2EntityConverter;
import com.zwedu.rac.infrastructure.mapper.DictionaryMapper;
import com.zwedu.rac.infrastructure.mapper.DictionaryNodeMapper;
import com.zwedu.rac.infrastructure.po.DictionaryPo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 字典存储类
 *
 * @author qingchuan
 * @date 2020/12/12
 */
@Repository
public class DictionaryMybatisRepository implements DictionaryRepository {
    @Resource
    private DictionaryMapper dictionaryMapper;
    @Resource
    private DictionaryNodeMapper dictionaryNodeMapper;

    @Override
    public Pagination<DictionaryEntity> listPage(Integer pageNo, Integer pageSize,
                                                 Long bizLineId, String searchVal) {
        try (Page page = PageHelper.startPage(pageNo, pageSize)) {
            List<DictionaryPo> poList = dictionaryMapper.listPage(bizLineId, searchVal);
            List<DictionaryEntity> entityList = DictionaryPo2EntityConverter.INSTANCE.toEntityList(poList);
            Pagination<DictionaryEntity> pagination =
                    new Pagination(pageNo, pageSize, page.getTotal());
            pagination.setDataList(entityList);
            return pagination;
        }
    }

    @Override
    public List<DictionaryEntity> listByBizLineId(Long bizLineId) {
        List<DictionaryPo> poList = dictionaryMapper.listByBizLineId(bizLineId);
        return DictionaryPo2EntityConverter.INSTANCE.toEntityList(poList);
    }


    /**
     * 根据ID查询实体信息
     *
     * @param id ID
     * @return 实体信息
     */
    @Override
    public DictionaryEntity queryById(Long id) {
        DictionaryPo record = dictionaryMapper.selectByPrimaryKey(id);
        return DictionaryPo2EntityConverter.INSTANCE.toEntity(record);
    }

    @Override
    public DictionaryEntity queryById(Long bizLineId, Long id) {
        DictionaryPo record = dictionaryMapper.queryById(bizLineId, id);
        return DictionaryPo2EntityConverter.INSTANCE.toEntity(record);
    }

    @Override
    public Boolean hasSameEnName(Long bizLineId, String enName, Long id) {
        return dictionaryMapper.queryByEnName(bizLineId, enName, id) != null;
    }

    @Override
    public Boolean hasSameCnName(Long bizLineId, String cnName, Long id) {
        return dictionaryMapper.queryByCnName(bizLineId, cnName, id) != null;
    }

    /**
     * 更新字典
     *
     * @param record 字典实体
     */
    @Override
    public void insert(DictionaryEntity record) {
        dictionaryMapper.insertSelective(DictionaryEntity2PoConverter.INSTANCE.toPo(record));
    }

    /**
     * 更新字典
     *
     * @param record 字典实体
     */
    @Override
    public void edit(DictionaryEntity record) {
        dictionaryMapper.updateByPrimaryKeySelective(DictionaryEntity2PoConverter.INSTANCE.toPo(record));
    }


    /**
     * 更新字典
     *
     * @param record 字典实体
     */
    @Override
    public void delete(DictionaryEntity record) {
        dictionaryMapper.updateByPrimaryKeySelective(DictionaryEntity2PoConverter.INSTANCE.toPo(record));
    }

    @Override
    public DictionaryEntity queryByEnName(Long bizLineId, String enName) {
        DictionaryPo record = dictionaryMapper.queryByEnName(bizLineId, enName, null);
        return DictionaryPo2EntityConverter.INSTANCE.toEntity(record);
    }
}
