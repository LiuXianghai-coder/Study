package com.zwedu.rac.application.service;

import com.zwedu.rac.application.converter.ExtPropertyEntity2ComplexDtoConverter;
import com.zwedu.rac.application.converter.ExtPropertyEntity2SimpleDtoConverter;
import com.zwedu.rac.application.converter.ExtPropertySimpleDto2EntityConverter;
import com.zwedu.rac.application.dto.ReqPaginationDto;
import com.zwedu.rac.application.dto.ResPaginationDto;
import com.zwedu.rac.application.dto.ext.ExtPropertyComplexDto;
import com.zwedu.rac.application.dto.ext.ExtPropertySimpleDto;
import com.zwedu.rac.domain.common.annotation.WriteAuth;
import com.zwedu.rac.domain.common.page.Pagination;
import com.zwedu.rac.domain.common.validator.ParamAssert;
import com.zwedu.rac.domain.entity.ExtPropertyEntity;
import com.zwedu.rac.domain.service.ExtPropertyDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 扩展属性应用层服务
 *
 * @author qingchuan
 * @date 2020/12/10
 */
@Service
@Slf4j
public class ExtPropertyAppService {
    @Resource
    private ExtPropertyDomainService extPropertyDomainService;

    /**
     * 查询扩展属性列表数据
     *
     * @param record 分页查询参数
     * @return 扩展属性列表数据
     */
    public ResPaginationDto<ExtPropertyComplexDto> listPage(ReqPaginationDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        // 查询对应的扩展属性列表
        Pagination<ExtPropertyEntity> pagination = extPropertyDomainService.listPage(record.getPageNo(),
                record.getPageSize(), record.getBizLineId(), record.getSearchVal());
        return ExtPropertyEntity2ComplexDtoConverter.INSTANCE.toPaginationDto(pagination);
    }

    /**
     * 查询授权的扩展属性列表
     *
     * @return 扩展属性列表数据
     */
    public List<ExtPropertySimpleDto> listByBizLineId(ExtPropertySimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        return ExtPropertyEntity2SimpleDtoConverter.INSTANCE.toDtoList(extPropertyDomainService
                .listByBizLineId(record.getBizLineId()));
    }


    /**
     * 查询授权的扩展属性列表
     *
     * @return 扩展属性列表数据
     */
    public List<ExtPropertySimpleDto> listByBizEntityEnName(ExtPropertySimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        return ExtPropertyEntity2SimpleDtoConverter.INSTANCE.toDtoList(extPropertyDomainService
                .listByBizEntityEnName(record.getBizLineId(), record.getBizEntityEnName()));
    }

    /**
     * 创建扩展属性
     *
     * @param currentUserId 登录用户ID
     * @param record 扩展属性实体
     */
    @WriteAuth
    public void create(Long currentUserId, ExtPropertySimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        extPropertyDomainService.create(currentUserId, ExtPropertySimpleDto2EntityConverter.INSTANCE.toEntity(record));
    }

    /**
     * 更新扩展属性
     *
     * @param currentUserId 登录用户ID
     * @param record 扩展属性实体
     */
    @WriteAuth
    public void edit(Long currentUserId, ExtPropertySimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        extPropertyDomainService.edit(currentUserId, ExtPropertySimpleDto2EntityConverter.INSTANCE.toEntity(record));
    }


    /**
     * 删除扩展属性
     *
     * @param currentUserId 登录用户ID
     * @param record 记录数据
     */
    @WriteAuth
    public void delete(Long currentUserId, ExtPropertySimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        extPropertyDomainService.delete(currentUserId, record.getBizLineId(), record.getBizEntityId(), record.getId());
    }

}
