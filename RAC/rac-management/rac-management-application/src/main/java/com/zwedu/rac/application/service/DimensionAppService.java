package com.zwedu.rac.application.service;

import com.zwedu.rac.application.converter.DimensionEntity2ComplexDtoConverter;
import com.zwedu.rac.application.converter.DimensionEntity2SimpleDtoConverter;
import com.zwedu.rac.application.converter.DimensionSimpleDto2EntityConverter;
import com.zwedu.rac.application.dto.ReqPaginationDto;
import com.zwedu.rac.application.dto.ResPaginationDto;
import com.zwedu.rac.application.dto.dimension.DimensionComplexDto;
import com.zwedu.rac.application.dto.dimension.DimensionSimpleDto;
import com.zwedu.rac.domain.common.annotation.WriteAuth;
import com.zwedu.rac.domain.common.page.Pagination;
import com.zwedu.rac.domain.common.validator.ParamAssert;
import com.zwedu.rac.domain.entity.DimensionEntity;
import com.zwedu.rac.domain.service.DimensionDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 维度应用层服务
 *
 * @author qingchuan
 * @date 2020/12/10
 */
@Service
@Slf4j
public class DimensionAppService {
    @Resource
    private DimensionDomainService dimensionDomainService;

    /**
     * 查询维度列表数据
     *
     * @param record 分页查询参数
     * @return 维度列表数据
     */
    public ResPaginationDto<DimensionComplexDto> listPage(ReqPaginationDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        // 查询对应的维度列表
        Pagination<DimensionEntity> pagination = dimensionDomainService.listPage(record.getPageNo(),
                record.getPageSize(), record.getBizLineId(), record.getSearchVal());
        return DimensionEntity2ComplexDtoConverter.INSTANCE.toPaginationDto(pagination);
    }

    /**
     * 查询授权的维度列表
     *
     * @return 维度列表数据
     */
    public List<DimensionSimpleDto> listByBizLineId(DimensionSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        return DimensionEntity2SimpleDtoConverter.INSTANCE.toDtoList(dimensionDomainService
                .listByBizLineId(record.getBizLineId()));
    }

    /**
     * 创建维度
     *
     * @param currentUserId 登录用户ID
     * @param record        维度实体
     */
    @WriteAuth
    public void create(Long currentUserId, DimensionSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        dimensionDomainService.create(currentUserId, DimensionSimpleDto2EntityConverter.INSTANCE.toEntity(record));
    }

    /**
     * 更新维度
     *
     * @param currentUserId 登录用户ID
     * @param record        维度实体
     */
    @WriteAuth
    public void edit(Long currentUserId, DimensionSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        dimensionDomainService.edit(currentUserId, DimensionSimpleDto2EntityConverter.INSTANCE.toEntity(record));
    }


    /**
     * 删除维度
     *
     * @param currentUserId 登录用户ID
     * @param record        记录数据
     */
    @WriteAuth
    public void delete(Long currentUserId, DimensionSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        dimensionDomainService.delete(currentUserId, record.getBizLineId(), record.getId());
    }


    /**
     * 根据维度英文名查询维度信息
     *
     * @param record 记录
     * @return 维度信息
     */
    public DimensionSimpleDto queryByEnName(DimensionSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        return DimensionEntity2SimpleDtoConverter.INSTANCE.toDto(dimensionDomainService
                .queryByEnName(record.getBizLineId(), record.getEnName()));
    }
}
