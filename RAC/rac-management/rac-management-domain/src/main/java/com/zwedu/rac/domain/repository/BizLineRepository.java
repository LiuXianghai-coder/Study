package com.zwedu.rac.domain.repository;


import com.zwedu.rac.domain.common.page.Pagination;
import com.zwedu.rac.domain.entity.BizLineEntity;

import java.util.List;

/**
 * 业务线
 *
 * @author qingchuan
 * @date 2020/12/10
 */
public interface BizLineRepository {
    /**
     * 查询业务线列表数据
     *
     * @param pageNo    当前页码
     * @param pageSize  分页大小
     * @param searchVal 检索值
     * @return 业务线列表数据
     */
    Pagination<BizLineEntity> listPage(Integer pageNo, Integer pageSize, String searchVal);

    /**
     * 查询授权的业务线列表
     *
     * @return 业务线列表数据
     */
    List<BizLineEntity> listAuthBizLine();

    /**
     * 根据ID查询实体信息
     *
     * @param id ID
     * @return 实体信息
     */
    BizLineEntity queryById(Long id);

    /**
     * 是否有重名的数据
     *
     * @param enName 英文名
     * @return true or false
     */
    Boolean hasSameEnName(String enName, Long id);

    /**
     * 是否有重名的数据
     *
     * @param cnName 中文名
     * @param id     数据ID
     * @return true or false
     */
    Boolean hasSameCnName(String cnName, Long id);

    /**
     * 新增业务线
     *
     * @param record 业务线实体
     */
    void insert(BizLineEntity record);

    /**
     * 更新业务线
     *
     * @param record 业务线实体
     */
    void edit(BizLineEntity record);

    /**
     * 删除业务线
     *
     * @param record 业务线实体
     */
    void delete(BizLineEntity record);

    /**
     * 通过英文查询
     *
     * @param enName 英文名
     * @return 业务线数据
     */
    BizLineEntity queryByEnName(String enName);
}
