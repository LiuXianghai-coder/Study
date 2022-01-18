package com.zwedu.rac.domain.service;

import com.zwedu.rac.domain.common.page.Pagination;
import com.zwedu.rac.domain.common.validator.BizAssert;
import com.zwedu.rac.domain.common.validator.ParamAssert;
import com.zwedu.rac.domain.entity.DictionaryEntity;
import com.zwedu.rac.domain.repository.BizLineRepository;
import com.zwedu.rac.domain.repository.DictionaryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 字典服务接口
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Service
public class DictionaryDomainService {
    @Resource
    private DictionaryRepository dictionaryRepository;
    @Resource
    private BizLineRepository bizLineRepository;

    /**
     * 查询字典列表数据
     *
     * @param pageNo    当前页码
     * @param pageSize  分页大小
     * @param searchVal 检索值
     * @return 字典列表数据
     */
    public Pagination<DictionaryEntity> listPage(Integer pageNo, Integer pageSize,
                                                 Long bizLineId, String searchVal) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(pageNo, pageSize, bizLineId);
        return dictionaryRepository.listPage(pageNo, pageSize, bizLineId, searchVal);
    }

    /**
     * 查询业务线对应的字典
     *
     * @param bizLineId 业务线ID
     * @return 字典列表
     */
    public List<DictionaryEntity> listByBizLineId(Long bizLineId) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(bizLineId);
        return dictionaryRepository.listByBizLineId(bizLineId);
    }

    /**
     * 创建字典
     *
     * @param currentUserId 登录用户ID
     * @param record        字典实体
     */
    @Transactional(rollbackFor = Throwable.class)
    public void create(Long currentUserId, DictionaryEntity record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, record);
        record.create(currentUserId);
        // 所属业务线是否存在
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(bizLineRepository.queryById(record.getBizLineId()), "业务线");
        // 检查英文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(dictionaryRepository
                .hasSameEnName(record.getBizLineId(), record.getEnName(), record.getId()), "字典英文名");
        // 检查中文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(dictionaryRepository
                .hasSameCnName(record.getBizLineId(), record.getCnName(), record.getId()), "中文名");
        dictionaryRepository.insert(record);
    }

    /**
     * 更新字典
     *
     * @param currentUserId 登录用户ID
     * @param record        字典实体
     */
    @Transactional(rollbackFor = Throwable.class)
    public void edit(Long currentUserId, DictionaryEntity record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, record);
        record.edit(currentUserId);
        // 所属业务线是否存在
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(bizLineRepository.queryById(record.getBizLineId()), "业务线");
        // 检查英文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(dictionaryRepository
                .hasSameEnName(record.getBizLineId(), record.getEnName(), record.getId()), "字典英文名");
        // 检查中文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(dictionaryRepository
                .hasSameCnName(record.getBizLineId(), record.getCnName(), record.getId()), "中文名");
        DictionaryEntity oldRecord = dictionaryRepository.queryById(record.getBizLineId(), record.getId());
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(oldRecord);
        dictionaryRepository.edit(record);
    }


    /**
     * 删除字典
     *
     * @param currentUserId 登录用户ID
     * @param id            记录ID
     */
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long currentUserId, Long bizLineId, Long id) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, bizLineId, id);
        DictionaryEntity record = dictionaryRepository.queryById(bizLineId, id);
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(record);
        record.delete(currentUserId);
        dictionaryRepository.delete(record);
    }

    /**
     * 根据英文名查询
     *
     * @param bizLineId 业务线ID
     * @param enName    英文名
     * @return 字典信息
     */
    public DictionaryEntity queryByEnName(Long bizLineId, String enName) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(bizLineId, enName);
        return dictionaryRepository.queryByEnName(bizLineId, enName);
    }

    /**
     * 通过ID查询字典详细信息
     *
     * @param bizLineId 业务线ID
     * @param id        字典ID
     * @return 字典信息
     */
    public DictionaryEntity queryById(Long bizLineId, Long id) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(bizLineId, id);
        return dictionaryRepository.queryById(bizLineId, id);
    }
}
