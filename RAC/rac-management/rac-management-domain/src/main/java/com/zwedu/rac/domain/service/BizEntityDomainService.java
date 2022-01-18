package com.zwedu.rac.domain.service;

import com.zwedu.rac.domain.common.annotation.NoneAuth;
import com.zwedu.rac.domain.common.page.Pagination;
import com.zwedu.rac.domain.common.validator.BizAssert;
import com.zwedu.rac.domain.common.validator.ParamAssert;
import com.zwedu.rac.domain.entity.BizEntity;
import com.zwedu.rac.domain.repository.BizEntityRepository;
import com.zwedu.rac.domain.repository.BizLineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务实体服务接口
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Service
public class BizEntityDomainService {
    @Resource
    private BizEntityRepository bizEntityRepository;
    @Resource
    private BizLineRepository bizLineRepository;

    /**
     * 查询业务实体列表数据
     *
     * @param pageNo    当前页码
     * @param pageSize  分页大小
     * @param searchVal 检索值
     * @return 业务实体列表数据
     */
    public Pagination<BizEntity> listPage(Integer pageNo, Integer pageSize,
                                          Long bizLineId, String searchVal) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(pageNo, pageSize, bizLineId);
        return bizEntityRepository.listPage(pageNo, pageSize, bizLineId, searchVal);
    }

    /**
     * 查询业务线对应的业务实体
     *
     * @param bizLineId 业务线ID
     * @return 业务实体列表
     */
    public List<BizEntity> listByBizLineId(Long bizLineId) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(bizLineId);
        return bizEntityRepository.listByBizLineId(bizLineId);
    }

    /**
     * 创建业务实体
     *
     * @param currentUserId 登录用户ID
     * @param record        业务实体实体
     */
    @Transactional(rollbackFor = Throwable.class)
    public void create(Long currentUserId, BizEntity record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, record);
        record.create(currentUserId);
        // 所属业务线是否存在
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(bizLineRepository.queryById(record.getBizLineId()), "业务线");
        // 检查英文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(bizEntityRepository
                .hasSameEnName(record.getBizLineId(), record.getEnName(), record.getId()), "英文名");
        // 检查中文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(bizEntityRepository
                .hasSameCnName(record.getBizLineId(), record.getCnName(), record.getId()), "中文名");
        bizEntityRepository.insert(record);
    }

    /**
     * 更新业务实体
     *
     * @param currentUserId 登录用户ID
     * @param record        业务实体实体
     */
    @Transactional(rollbackFor = Throwable.class)
    public void edit(Long currentUserId, BizEntity record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, record);
        record.edit(currentUserId);
        // 所属业务线是否存在
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(bizLineRepository.queryById(record.getBizLineId()), "业务线");
        // 检查英文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(bizEntityRepository
                .hasSameEnName(record.getBizLineId(), record.getEnName(), record.getId()), "英文名");
        // 检查中文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(bizEntityRepository
                .hasSameCnName(record.getBizLineId(), record.getCnName(), record.getId()), "中文名");
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(bizEntityRepository.queryById(record.getBizLineId(), record.getId()));
        bizEntityRepository.edit(record);
    }


    /**
     * 删除业务实体
     *
     * @param currentUserId 登录用户ID
     * @param id            记录ID
     */
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long currentUserId, Long bizLineId, Long id) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, bizLineId, id);
        BizEntity record = bizEntityRepository.queryById(bizLineId, id);
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(record);
        record.delete(currentUserId);
        bizEntityRepository.delete(record);
    }

    /**
     * 通过英文名查询
     *
     * @param bizLineId 业务线ID
     * @param enName    英文名
     * @return 实体
     */
    @NoneAuth
    public BizEntity queryByEnName(Long bizLineId, String enName) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(bizLineId, enName);
        return bizEntityRepository.queryByEnName(bizLineId, enName);
    }
}
