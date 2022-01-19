package com.zwedu.cust.service;

import com.google.common.collect.Lists;
import com.zwedu.cust.entity.Cust;
import com.zwedu.cust.mapper.CustMapper;
import com.zwedu.rac.sdk.provider.AuthProvider;
import com.zwedu.rac.sdk.rdo.DimensionNodeRdo;
import com.zwedu.rac.sdk.rpo.DimensionAuthRpo;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.zwedu.cust.config.SystemConstant.BIZ_LINE_ID;
import static com.zwedu.cust.config.SystemConstant.POSITION_EN_NAME;

/**
 * 客户管理服务层
 *
 * @author qingchuan
 * @date 2020/12/26
 */
@Service
public class CustService {
    @DubboReference
    private AuthProvider authProvider;
    @Resource
    private CustMapper custMapper;

    /**
     * 获取销售对应的客户列表
     *
     * @param userId 用户ID
     * @return 客户列表
     */
    public List<Cust> listCust(Long userId) {
        DimensionAuthRpo rpo = new DimensionAuthRpo();
        rpo.setBizLineId(BIZ_LINE_ID);
        rpo.setUserId(userId);
        rpo.setDimensionEnName(POSITION_EN_NAME);
        List<DimensionNodeRdo> dimensionNodeRdoList = authProvider.listDimensionNodeWithChildByUser(rpo);
        if (CollectionUtils.isEmpty(dimensionNodeRdoList)) {
            return Lists.newArrayList();
        }
        List<Long> posIdList = dimensionNodeRdoList.stream().map(input->input.getId()).collect(Collectors.toList());
        return custMapper.listByPosId(posIdList);
    }
}
