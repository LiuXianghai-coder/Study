package com.zwedu.rac.application.service;

import com.zwedu.rac.application.converter.DictionaryEntity2ComplexDtoConverter;
import com.zwedu.rac.application.converter.DictionaryEntity2SimpleDtoConverter;
import com.zwedu.rac.application.converter.DictionarySimpleDto2EntityConverter;
import com.zwedu.rac.application.dto.ReqPaginationDto;
import com.zwedu.rac.application.dto.ResPaginationDto;
import com.zwedu.rac.application.dto.dictionary.DictionaryComplexDto;
import com.zwedu.rac.application.dto.dictionary.DictionarySimpleDto;
import com.zwedu.rac.domain.common.annotation.WriteAuth;
import com.zwedu.rac.domain.common.page.Pagination;
import com.zwedu.rac.domain.common.validator.ParamAssert;
import com.zwedu.rac.domain.entity.DictionaryEntity;
import com.zwedu.rac.domain.service.DictionaryDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 字典应用层服务
 *
 * @author qingchuan
 * @date 2020/12/10
 */
@Service
@Slf4j
public class DictionaryAppService {
    @Resource
    private DictionaryDomainService dictionaryDomainService;

    /**
     * 查询字典列表数据
     *
     * @param record 分页查询参数
     * @return 字典列表数据
     */
    public ResPaginationDto<DictionaryComplexDto> listPage(ReqPaginationDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        // 查询对应的字典列表
        Pagination<DictionaryEntity> pagination = dictionaryDomainService.listPage(record.getPageNo(),
                record.getPageSize(), record.getBizLineId(), record.getSearchVal());
        return DictionaryEntity2ComplexDtoConverter.INSTANCE.toPaginationDto(pagination);
    }

    /**
     * 查询授权的字典列表
     *
     * @return 字典列表数据
     */
    public List<DictionarySimpleDto> listByBizLineId(DictionarySimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        return DictionaryEntity2SimpleDtoConverter.INSTANCE.toDtoList(dictionaryDomainService
                .listByBizLineId(record.getBizLineId()));
    }

    /**
     * 创建字典
     *
     * @param currentUserId 登录用户ID
     * @param record 字典实体
     */
    @WriteAuth
    public void create(Long currentUserId, DictionarySimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        dictionaryDomainService.create(currentUserId, DictionarySimpleDto2EntityConverter.INSTANCE.toEntity(record));
    }

    /**
     * 更新字典
     *
     * @param currentUserId 登录用户ID
     * @param record 字典实体
     */
    @WriteAuth
    public void edit(Long currentUserId, DictionarySimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        dictionaryDomainService.edit(currentUserId, DictionarySimpleDto2EntityConverter.INSTANCE.toEntity(record));
    }


    /**
     * 删除字典
     *
     * @param currentUserId 登录用户ID
     * @param record 记录数据
     */
    @WriteAuth
    public void delete(Long currentUserId, DictionarySimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        dictionaryDomainService.delete(currentUserId, record.getBizLineId(), record.getId());
    }


    /**
     * 根据英文名查询字典信息
     *
     * @param record 记录
     * @return 字典信息
     */
    public DictionaryComplexDto queryByEnName(DictionarySimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        return DictionaryEntity2ComplexDtoConverter.INSTANCE.toDto(dictionaryDomainService
                .queryByEnName(record.getBizLineId(), record.getEnName()));
    }
}
