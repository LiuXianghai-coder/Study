package com.zwedu.rac.application.service;

import com.zwedu.rac.application.converter.BizEntity2ComplexDtoConverter;
import com.zwedu.rac.application.converter.BizEntity2SimpleDtoConverter;
import com.zwedu.rac.application.converter.BizEntitySimpleDto2EntityConverter;
import com.zwedu.rac.application.dto.ReqPaginationDto;
import com.zwedu.rac.application.dto.ResPaginationDto;
import com.zwedu.rac.application.dto.bizentity.BizEntityComplexDto;
import com.zwedu.rac.application.dto.bizentity.BizEntitySimpleDto;
import com.zwedu.rac.domain.common.annotation.WriteAuth;
import com.zwedu.rac.domain.common.page.Pagination;
import com.zwedu.rac.domain.common.validator.ParamAssert;
import com.zwedu.rac.domain.entity.BizEntity;
import com.zwedu.rac.domain.service.BizEntityDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务实体应用层服务
 *
 * @author qingchuan
 * @date 2020/12/10
 */
@Service
@Slf4j
public class BizEntityAppService {
    @Resource
    private BizEntityDomainService bizEntityDomainService;

    /**
     * 查询业务实体列表数据
     *
     * @param record 分页查询参数
     * @return 业务实体列表数据
     */
    @WriteAuth
    public ResPaginationDto<BizEntityComplexDto> listPage(ReqPaginationDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        // 查询对应的业务实体列表
        Pagination<BizEntity> pagination = bizEntityDomainService.listPage(record.getPageNo(),
                record.getPageSize(), record.getBizLineId(), record.getSearchVal());
        return BizEntity2ComplexDtoConverter.INSTANCE.toPaginationDto(pagination);
    }

    /**
     * 查询授权的业务实体列表
     *
     * @return 业务实体列表数据
     */
    public List<BizEntitySimpleDto> listByBizLineId(BizEntitySimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        return BizEntity2SimpleDtoConverter.INSTANCE.toDtoList(bizEntityDomainService
                .listByBizLineId(record.getBizLineId()));
    }

    /**
     * 创建业务实体
     *
     * @param currentUserId 登录用户ID
     * @param record 业务实体实体
     */
    @WriteAuth
    public void create(Long currentUserId, BizEntitySimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        bizEntityDomainService.create(currentUserId, BizEntitySimpleDto2EntityConverter.INSTANCE.toEntity(record));
    }

    /**
     * 更新业务实体
     *
     * @param currentUserId 登录用户ID
     * @param record 业务实体实体
     */
    @WriteAuth
    public void edit(Long currentUserId, BizEntitySimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        bizEntityDomainService.edit(currentUserId, BizEntitySimpleDto2EntityConverter.INSTANCE.toEntity(record));
    }


    /**
     * 删除业务实体
     *
     * @param currentUserId 登录用户ID
     * @param record 记录数据
     */
    @WriteAuth
    public void delete(Long currentUserId, BizEntitySimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        bizEntityDomainService.delete(currentUserId, record.getBizLineId(), record.getId());
    }


}
