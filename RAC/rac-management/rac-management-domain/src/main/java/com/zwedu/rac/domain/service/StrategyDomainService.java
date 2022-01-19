package com.zwedu.rac.domain.service;

import com.google.common.collect.Maps;
import com.zwedu.rac.domain.common.page.Pagination;
import com.zwedu.rac.domain.common.util.Collections2;
import com.zwedu.rac.domain.common.validator.BizAssert;
import com.zwedu.rac.domain.common.validator.ParamAssert;
import com.zwedu.rac.domain.entity.StrategyEntity;
import com.zwedu.rac.domain.repository.BizLineRepository;
import com.zwedu.rac.domain.repository.StrategyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 访问策略服务接口
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Service
public class StrategyDomainService {
    @Resource
    private StrategyRepository strategyRepository;
    @Resource
    private BizLineRepository bizLineRepository;

    /**
     * 查询访问策略列表数据
     *
     * @param pageNo    当前页码
     * @param pageSize  分页大小
     * @param searchVal 检索值
     * @return 访问策略列表数据
     */
    public Pagination<StrategyEntity> listPage(Integer pageNo, Integer pageSize,
                                               Long bizLineId, String searchVal) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(pageNo, pageSize, bizLineId);
        return strategyRepository.listPage(pageNo, pageSize, bizLineId, searchVal);
    }

    /**
     * 查询业务线对应的访问策略
     *
     * @param bizLineId 业务线ID
     * @return 访问策略列表
     */
    public List<StrategyEntity> listByBizLineId(Long bizLineId) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(bizLineId);
        return strategyRepository.listByBizLineId(bizLineId);
    }

    /**
     * 创建访问策略
     *
     * @param currentUserId 登录用户ID
     * @param record        访问策略实体
     */
    @Transactional(rollbackFor = Throwable.class)
    public void create(Long currentUserId, StrategyEntity record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, record);
        record.create(currentUserId);
        // 所属业务线是否存在
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(bizLineRepository.queryById(record.getBizLineId()), "业务线");
        // 检查英文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(strategyRepository
                .hasSameEnName(record.getBizLineId(), record.getEnName(), record.getId()), "访问策略英文名");
        // 检查中文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(strategyRepository
                .hasSameCnName(record.getBizLineId(), record.getCnName(), record.getId()), "中文名");
        strategyRepository.insert(record);
    }

    /**
     * 更新访问策略
     *
     * @param currentUserId 登录用户ID
     * @param record        访问策略实体
     */
    @Transactional(rollbackFor = Throwable.class)
    public void edit(Long currentUserId, StrategyEntity record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, record);
        record.edit(currentUserId);
        // 所属业务线是否存在
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(bizLineRepository.queryById(record.getBizLineId()), "业务线");
        // 检查英文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(strategyRepository
                .hasSameEnName(record.getBizLineId(), record.getEnName(), record.getId()), "访问策略英文名");
        // 检查中文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(strategyRepository
                .hasSameCnName(record.getBizLineId(), record.getCnName(), record.getId()), "中文名");
        strategyRepository.edit(record);
    }


    /**
     * 删除访问策略
     *
     * @param currentUserId 登录用户ID
     * @param id            记录ID
     */
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long currentUserId, Long bizLineId, Long id) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, bizLineId, id);
        StrategyEntity record = strategyRepository.queryById(bizLineId, id);
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(record);
        record.delete(currentUserId);
        strategyRepository.delete(record);
    }

    /**
     * 根据策略ID获取访问策略信息
     *
     * @param bizLineId 业务线ID
     * @param funcIds   功能ID
     */
    public Map<Long, StrategyEntity> listByIds(Long bizLineId, Collection<Long> funcIds) {
        if (CollectionUtils.isEmpty(funcIds) || bizLineId == null) {
            return Maps.newHashMap();
        }
        List<StrategyEntity> strategyEntityList = strategyRepository.listByIds(bizLineId, funcIds);
        return Collections2.toMap(strategyEntityList, input -> input.getId(), input -> input);
    }
}
