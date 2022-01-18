package com.zwedu.rac.infrastructure.repository;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwedu.rac.domain.common.page.Pagination;
import com.zwedu.rac.domain.entity.ExtDataEntity;
import com.zwedu.rac.domain.entity.ExtPropertyEntity;
import com.zwedu.rac.domain.repository.ExtPropertyRepository;
import com.zwedu.rac.infrastructure.converter.*;
import com.zwedu.rac.infrastructure.mapper.ExtDataMapper;
import com.zwedu.rac.infrastructure.mapper.ExtPropertyMapper;
import com.zwedu.rac.infrastructure.po.ExtData;
import com.zwedu.rac.infrastructure.po.ExtPropertyPo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 扩展属性存储类
 *
 * @author qingchuan
 * @date 2020/12/12
 */
@Repository
public class ExtPropertyMybatisRepository implements ExtPropertyRepository {
    @Resource
    private ExtPropertyMapper extPropertyMapper;
    @Resource
    private ExtDataMapper extDataMapper;

    @Override
    public Pagination<ExtPropertyEntity> listPage(Integer pageNo, Integer pageSize,
                                                  Long bizLineId, String searchVal) {
        try (Page page = PageHelper.startPage(pageNo, pageSize)) {
            List<ExtPropertyPo> poList = extPropertyMapper.listPage(bizLineId, searchVal);
            List<ExtPropertyEntity> entityList = ExtPropertyPo2EntityConverter.INSTANCE.toEntityList(poList);
            Pagination<ExtPropertyEntity> pagination =
                    new Pagination(pageNo, pageSize, page.getTotal());
            pagination.setDataList(entityList);
            return pagination;
        }
    }

    @Override
    public List<ExtPropertyEntity> listByBizLineId(Long bizLineId) {
        List<ExtPropertyPo> poList = extPropertyMapper.listByBizLineId(bizLineId);
        return ExtPropertyPo2EntityConverter.INSTANCE.toEntityList(poList);
    }


    @Override
    public List<ExtPropertyEntity> listByBizEntityId(Long bizLineId, Long bizEntityId) {
        List<ExtPropertyPo> poList = extPropertyMapper.listByBizEntityId(bizLineId, bizEntityId);
        return ExtPropertyPo2EntityConverter.INSTANCE.toEntityList(poList);
    }

    @Override
    public ExtPropertyEntity queryById(Long id) {
        ExtPropertyPo record = extPropertyMapper.selectByPrimaryKey(id);
        return ExtPropertyPo2EntityConverter.INSTANCE.toEntity(record);
    }

    @Override
    public ExtPropertyEntity queryById(Long bizLineId, Long bizEntityId, Long id) {
        ExtPropertyPo record = extPropertyMapper.queryById(bizLineId, bizEntityId, id);
        return ExtPropertyPo2EntityConverter.INSTANCE.toEntity(record);
    }

    @Override
    public Boolean hasSameEnName(Long bizLineId, Long bizEntityId, String enName, Long id) {
        return extPropertyMapper.queryByEnName(bizLineId, bizEntityId, enName, id) != null;
    }

    @Override
    public Boolean hasSameCnName(Long bizLineId, Long bizEntityId, String cnName, Long id) {
        return extPropertyMapper.queryByCnName(bizLineId, bizEntityId, cnName, id) != null;
    }

    /**
     * 更新扩展属性
     *
     * @param record 扩展属性实体
     */
    @Override
    public void insert(ExtPropertyEntity record) {
        extPropertyMapper.insertSelective(ExtPropertyEntity2PoConverter.INSTANCE.toPo(record));
    }

    /**
     * 更新扩展属性
     *
     * @param record 扩展属性实体
     */
    @Override
    public void edit(ExtPropertyEntity record) {
        extPropertyMapper.updateByPrimaryKeySelective(ExtPropertyEntity2PoConverter.INSTANCE.toPo(record));
    }


    /**
     * 更新扩展属性
     *
     * @param record 扩展属性实体
     */
    @Override
    public void delete(ExtPropertyEntity record) {
        extPropertyMapper.updateByPrimaryKeySelective(ExtPropertyEntity2PoConverter.INSTANCE.toPo(record));
    }

    @Override
    public List<ExtPropertyEntity> listByIds(List<Long> ids) {
        List<ExtPropertyPo> poList = extPropertyMapper.listByIds(ids);
        return ExtPropertyPo2EntityConverter.INSTANCE.toEntityList(poList);
    }

    @Override
    public List<ExtDataEntity> listDataByBizId(Long bizEntityId, Long bizDataId) {
        List<ExtData> extDataList = extDataMapper.listDataByBizId(bizEntityId, bizDataId);
        return ExtData2EntityConverter.INSTANCE.toEntityList(extDataList);
    }



    @Override
    public void addExtProperty(ExtDataEntity record) {
        extDataMapper.dropExtProperty(record.getBizLineId(), record.getExtPropertyId(), record.getBizDataId());
        extDataMapper.insertSelective(ExtDataEntity2PoConverter.INSTANCE.toPo(record));
    }


    @Override
    public void dropExtProperty(ExtDataEntity record) {
        extDataMapper.dropExtProperty(record.getBizLineId(), record.getExtPropertyId(), record.getBizDataId());
    }

    @Override
    public List<ExtDataEntity> listExtProperty(Long bizLineId, Long userId) {
        return ExtDataPo2EntityConverter.INSTANCE.toEntityList(extDataMapper
                .listExtProperty(bizLineId, userId));
    }
}
