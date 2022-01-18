package com.zwedu.rac.domain.service;

import com.google.common.collect.Lists;
import com.zwedu.rac.domain.common.page.Pagination;
import com.zwedu.rac.domain.common.validator.BizAssert;
import com.zwedu.rac.domain.common.validator.ParamAssert;
import com.zwedu.rac.domain.entity.BizEntity;
import com.zwedu.rac.domain.entity.ExtDataEntity;
import com.zwedu.rac.domain.entity.ExtPropertyEntity;
import com.zwedu.rac.domain.repository.BizEntityRepository;
import com.zwedu.rac.domain.repository.BizLineRepository;
import com.zwedu.rac.domain.repository.ExtPropertyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 扩展属性服务接口
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Service
public class ExtPropertyDomainService {
    @Resource
    private ExtPropertyRepository extPropertyRepository;
    @Resource
    private BizLineRepository bizLineRepository;
    @Resource
    private BizEntityRepository bizEntityRepository;

    /**
     * 查询扩展属性列表数据
     *
     * @param pageNo    当前页码
     * @param pageSize  分页大小
     * @param searchVal 检索值
     * @return 扩展属性列表数据
     */
    public Pagination<ExtPropertyEntity> listPage(Integer pageNo, Integer pageSize,
                                                  Long bizLineId, String searchVal) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(pageNo, pageSize, bizLineId);
        return extPropertyRepository.listPage(pageNo, pageSize, bizLineId, searchVal);
    }

    /**
     * 查询业务线对应的扩展属性
     *
     * @param bizLineId 业务线ID
     * @return 扩展属性列表
     */
    public List<ExtPropertyEntity> listByBizLineId(Long bizLineId) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(bizLineId);
        return extPropertyRepository.listByBizLineId(bizLineId);
    }


    /**
     * 扩展属性数据
     *
     * @param bizEntityId 业务实体ID
     * @param bizDataId   业务数据ID
     * @return 扩展属性列表
     */
    public List<ExtDataEntity> listDataByBizId(Long bizEntityId, Long bizDataId) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(bizEntityId, bizDataId);
        return extPropertyRepository.listDataByBizId(bizEntityId, bizDataId);
    }

    /**
     * 查询业务线对应的扩展属性
     *
     * @param bizLineId       业务线ID
     * @param bizEntityEnName 业务实体英文名
     * @return 扩展属性列表
     */
    public List<ExtPropertyEntity> listByBizEntityEnName(Long bizLineId, String bizEntityEnName) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(bizLineId, bizEntityEnName);
        BizEntity bizEntity = bizEntityRepository.queryByEnName(bizLineId, bizEntityEnName);
        if (bizEntity == null) {
            return Lists.newArrayList();
        }
        return extPropertyRepository.listByBizEntityId(bizLineId, bizEntity.getId());
    }

    /**
     * 查询产品线下业务数据的扩展属性
     *
     * @param bizLineId 业务线ID
     * @param bizDataId 业务数据ID
     * @return 扩展属性列表
     */
    public List<ExtDataEntity> listExtProperty(Long bizLineId, String bizEntityEnName, Long bizDataId) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(bizLineId, bizEntityEnName, bizDataId);
        BizEntity bizEntity = bizEntityRepository.queryByEnName(bizLineId, bizEntityEnName);
        if (bizEntity == null) {
            return Lists.newArrayList();
        }
        return extPropertyRepository.listExtProperty(bizEntity.getId(), bizDataId);
    }

    /**
     * 添加扩展属性
     *
     * @param currentUserId 登录用户ID
     * @param record        记录数据
     */
    @Transactional(rollbackFor = Throwable.class)
    public void addExtProperty(Long currentUserId, ExtDataEntity record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, record);
        record.addExtValue(currentUserId);
        extPropertyRepository.addExtProperty(record);
    }

    /**
     * 删除扩展属性
     *
     * @param record 记录数据
     */
    @Transactional(rollbackFor = Throwable.class)
    public void dropExtProperty(ExtDataEntity record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        extPropertyRepository.dropExtProperty(record);
    }

    /**
     * 创建扩展属性
     *
     * @param currentUserId 登录用户ID
     * @param record        扩展属性实体
     */
    @Transactional(rollbackFor = Throwable.class)
    public void create(Long currentUserId, ExtPropertyEntity record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, record);
        record.create(currentUserId);
        // 所属业务线是否存在
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(bizLineRepository.queryById(record.getBizLineId()), "业务线");
        // 所属业务实体是否存在
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(bizEntityRepository
                .queryById(record.getBizEntityId()), "业务实体");
        // 检查英文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(extPropertyRepository
                .hasSameEnName(record.getBizLineId(), record.getBizEntityId(), record.getEnName(),
                        record.getId()), "英文名");
        // 检查中文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(extPropertyRepository
                .hasSameCnName(record.getBizLineId(), record.getBizEntityId(), record.getCnName(),
                        record.getId()), "中文名");
        extPropertyRepository.insert(record);
    }

    /**
     * 更新扩展属性
     *
     * @param currentUserId 登录用户ID
     * @param record        扩展属性实体
     */
    @Transactional(rollbackFor = Throwable.class)
    public void edit(Long currentUserId, ExtPropertyEntity record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, record);
        record.edit(currentUserId);
        // 所属业务线是否存在
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(bizLineRepository.queryById(record.getBizLineId()), "业务线");
        // 所属业务实体是否存在
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(bizEntityRepository
                .queryById(record.getBizEntityId()), "业务实体");
        // 检查英文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(extPropertyRepository
                .hasSameEnName(record.getBizLineId(), record.getBizEntityId(), record.getEnName(),
                        record.getId()), "英文名");
        // 检查中文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(extPropertyRepository
                .hasSameCnName(record.getBizLineId(), record.getBizEntityId(), record.getCnName(),
                        record.getId()), "中文名");
        extPropertyRepository.edit(record);
    }


    /**
     * 删除扩展属性
     *
     * @param currentUserId 登录用户ID
     * @param id            记录ID
     */
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long currentUserId, Long bizLineId, Long bizEntityId, Long id) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, bizLineId, id);
        ExtPropertyEntity record = extPropertyRepository.queryById(bizLineId, bizEntityId, id);
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(record);
        record.delete(currentUserId);
        extPropertyRepository.delete(record);
    }

    /**
     * 根据ID列表查询扩展属性
     *
     * @param ids id列表
     * @return 扩展属性列表
     */
    public List<ExtPropertyEntity> listByIds(List<Long> ids) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(ids);
        return extPropertyRepository.listByIds(ids);
    }
}
