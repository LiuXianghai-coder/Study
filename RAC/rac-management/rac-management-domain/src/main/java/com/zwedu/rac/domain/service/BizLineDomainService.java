package com.zwedu.rac.domain.service;

import com.zwedu.rac.domain.common.page.Pagination;
import com.zwedu.rac.domain.common.validator.BizAssert;
import com.zwedu.rac.domain.common.validator.ParamAssert;
import com.zwedu.rac.domain.entity.BizLineEntity;
import com.zwedu.rac.domain.repository.BizLineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务线服务接口
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Service
public class BizLineDomainService {
    @Resource
    private BizLineRepository bizLineRepository;

    /**
     * 查询业务线列表数据
     *
     * @param pageNo    当前页码
     * @param pageSize  分页大小
     * @param searchVal 检索值
     * @return 业务线列表数据
     */
    public Pagination<BizLineEntity> listPage(Integer pageNo, Integer pageSize, String searchVal) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(pageNo, pageSize);
        return bizLineRepository.listPage(pageNo, pageSize, searchVal);
    }


    /**
     * 查询授权的业务线列表
     *
     * @return 业务线列表数据
     */
    public List<BizLineEntity> listAuthBizLine() {
        return bizLineRepository.listAuthBizLine();
    }

    /**
     * 根据英文名查询
     *
     * @param enName 英文名
     * @return 业务线实体
     */
    public BizLineEntity queryByEnName(String enName) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(enName);
        return bizLineRepository.queryByEnName(enName);
    }

    /**
     * 创建业务线
     *
     * @param currentUserId 登录用户ID
     * @param record        业务线实体
     */
    @Transactional(rollbackFor = Throwable.class)
    public void create(Long currentUserId, BizLineEntity record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, record);
        record.create(currentUserId);
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(bizLineRepository
                .hasSameEnName(record.getEnName(), record.getId()), "英文名");
        // 检查中文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(bizLineRepository
                .hasSameCnName(record.getCnName(), record.getId()), "中文名");
        bizLineRepository.insert(record);
    }

    /**
     * 更新业务线
     *
     * @param currentUserId 登录用户ID
     * @param record        业务线实体
     */
    @Transactional(rollbackFor = Throwable.class)
    public void edit(Long currentUserId, BizLineEntity record) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, record);
        record.edit(currentUserId);
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(bizLineRepository
                .hasSameEnName(record.getEnName(), record.getId()), "英文名");
        // 检查中文名是否重复
        BizAssert.DUPLICATE_RECORD_ERROR.notTrue(bizLineRepository
                .hasSameCnName(record.getCnName(), record.getId()), "中文名");
        BizLineEntity oldRecord = bizLineRepository.queryById(record.getId());
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(oldRecord);
        bizLineRepository.edit(record);
    }


    /**
     * 删除业务线
     *
     * @param currentUserId 登录用户ID
     * @param id            记录ID
     */
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long currentUserId, Long id) {
        ParamAssert.PARAM_EMPTY_ERROR.allNotNull(currentUserId, id);
        BizLineEntity record = bizLineRepository.queryById(id);
        BizAssert.NOT_EXIST_RECORD_ERROR.notNull(record);
        record.delete(currentUserId);
        bizLineRepository.delete(record);
    }

}
