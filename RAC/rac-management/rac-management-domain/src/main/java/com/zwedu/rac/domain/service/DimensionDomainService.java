package com.zwedu.rac.domain.service;

import com.zwedu.rac.domain.common.annotation.NoneAuth;
import com.zwedu.rac.domain.common.page.Pagination;
import com.zwedu.rac.domain.common.validator.BizAssert;
import com.zwedu.rac.domain.common.validator.ParamAssert;
import com.zwedu.rac.domain.entity.DimensionEntity;
import com.zwedu.rac.domain.repository.BizLineRepository;
import com.zwedu.rac.domain.repository.DictionaryRepository;
import com.zwedu.rac.domain.repository.DimensionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 维度服务接口
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Service
public class DimensionDomainService {
    @Resource
    private DimensionRepository dimensionRepository;
    @Resource
    private BizLineRepository bizLineRepository;
    @Resource
    private DictionaryRepository dictionaryRepository;

    /**
     * 查询维度列表数据
     *
     * @param pageNo    当前页码
     * @param pageSize  分页大小
     * @param searchVal 检索值
     * @return 维度列表数据
     */
    public Pagination<DimensionEntity> listPage(Integer pageNo, Integer pageSize,
                                                Long bizLineId, String searchVal) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(pageNo, pageSize, bizLineId);
        return dimensionRepository.listPage(pageNo, pageSize, bizLineId, searchVal);
    }

    /**
     * 查询业务线对应的维度
     *
     * @param bizLineId 业务线ID
     * @return 维度列表
     */
    public List<DimensionEntity> listByBizLineId(Long bizLineId) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(bizLineId);
        return dimensionRepository.listByBizLineId(bizLineId);
    }

    /**
     * 通过英文名ing查询
     *
     * @param bizLineId 业务线ID
     * @param enName    维度英文名
     */
    @NoneAuth
    public DimensionEntity queryByEnName(Long bizLineId, String enName) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(bizLineId, enName);
        return dimensionRepository.queryByEnName(bizLineId, enName);
    }

    /**
     * 根据ID今夕查询
     *
     * @param bizLineId 业务线ID
     * @param id        维度ID
     * @return 维度实体
     */
    public DimensionEntity queryById(Long bizLineId, Long id) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(bizLineId, id);
        return dimensionRepository.queryById(bizLineId, id);
    }

    /**
     * 创建维度
     *
     * @param currentUserId 登录用户ID
     * @param record        维度实体
     */
    @Transactional(rollbackFor = Throwable.class)
    public void create(Long currentUserId, DimensionEntity record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, record);
        record.create(currentUserId);
        // 所属业务线是否存在
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(bizLineRepository.queryById(record.getBizLineId()), "业务线");
        // 检查维度节点类型值是否存在
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(dictionaryRepository.queryById(record.getBizLineId(),
                record.getNodeTypeId()), "维度节点类型");
        // 检查英文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(dimensionRepository
                .hasSameEnName(record.getBizLineId(), record.getEnName(),
                        record.getId()), "英文名");
        // 检查中文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(dimensionRepository
                .hasSameCnName(record.getBizLineId(), record.getCnName(),
                        record.getId()), "中文名");
        dimensionRepository.insert(record);
    }

    /**
     * 更新维度
     *
     * @param currentUserId 登录用户ID
     * @param record        维度实体
     */
    @Transactional(rollbackFor = Throwable.class)
    public void edit(Long currentUserId, DimensionEntity record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, record);
        record.edit(currentUserId);
        // 所属业务线是否存在
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(bizLineRepository.queryById(record.getBizLineId()), "业务线");
        // 检查维度节点类型值是否存在
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(dictionaryRepository.queryById(record.getBizLineId(),
                record.getNodeTypeId()), "维度节点类型");
        // 检查英文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(dimensionRepository
                .hasSameEnName(record.getBizLineId(), record.getEnName(),
                        record.getId()), "英文名");
        // 检查中文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(dimensionRepository
                .hasSameCnName(record.getBizLineId(), record.getCnName(),
                        record.getId()), "中文名");
        DimensionEntity oldRecord = dimensionRepository.queryById(record.getBizLineId(), record.getId());
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(oldRecord);
        dimensionRepository.edit(record);
    }


    /**
     * 删除维度
     *
     * @param currentUserId 登录用户ID
     * @param id            记录ID
     */
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long currentUserId, Long bizLineId, Long id) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, bizLineId, id);
        DimensionEntity record = dimensionRepository.queryById(bizLineId, id);
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(record);
        record.delete(currentUserId);
        dimensionRepository.delete(record);
    }

}
