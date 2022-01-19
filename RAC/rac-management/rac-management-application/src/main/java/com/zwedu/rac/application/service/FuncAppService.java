package com.zwedu.rac.application.service;

import com.zwedu.rac.application.common.TreeBuilder;
import com.zwedu.rac.application.converter.FuncEntity2ComplexDtoConverter;
import com.zwedu.rac.application.converter.FuncSimpleDto2EntityConverter;
import com.zwedu.rac.application.dto.func.FuncComplexDto;
import com.zwedu.rac.application.dto.func.FuncSimpleDto;
import com.zwedu.rac.domain.common.annotation.WriteAuth;
import com.zwedu.rac.domain.common.validator.ParamAssert;
import com.zwedu.rac.domain.entity.FuncEntity;
import com.zwedu.rac.domain.service.FuncDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能应用层服务
 *
 * @author qingchuan
 * @date 2020/12/10
 */
@Service
@Slf4j
public class FuncAppService {
    @Resource
    private FuncDomainService funcDomainService;


    /**
     * 查询子功能
     *
     * @param record 查询参数
     * @return 功能列表数据
     */
    public List<FuncComplexDto> listByParentId(FuncSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        // 查询对应的功能列表
        List<FuncEntity> funcEntityList = funcDomainService.
                listByParentId(record.getBizLineId(), record.getParentId());
        return FuncEntity2ComplexDtoConverter.INSTANCE.toDtoList(funcEntityList);
    }


    /**
     * 查询子功能
     *
     * @param record 查询参数
     * @return 功能列表数据
     */
    public List<FuncComplexDto> listByBizLineId(FuncSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        // 查询对应的功能列表
        List<FuncEntity> funcEntityList = funcDomainService.
                listByBizLineId(record.getBizLineId(), record.getSearchVal());
        List<FuncComplexDto> funcComplexDtoList = FuncEntity2ComplexDtoConverter.INSTANCE.toDtoList(funcEntityList);
        return TreeBuilder.buildTree(funcComplexDtoList);
    }

    /**
     * 创建功能
     *
     * @param currentUserId 登录用户ID
     * @param record 功能实体
     */
    @WriteAuth
    public void create(Long currentUserId, FuncSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        funcDomainService.create(currentUserId, FuncSimpleDto2EntityConverter.INSTANCE.toEntity(record));
    }

    /**
     * 更新功能
     *
     * @param currentUserId 登录用户ID
     * @param record 功能实体
     */
    @WriteAuth
    public void edit(Long currentUserId, FuncSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        funcDomainService.edit(currentUserId, FuncSimpleDto2EntityConverter.INSTANCE.toEntity(record));
    }


    /**
     * 删除功能
     *
     * @param currentUserId 登录用户ID
     * @param record 记录数据
     */
    @WriteAuth
    public void delete(Long currentUserId, FuncSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        funcDomainService.delete(currentUserId, record.getBizLineId(), record.getId());
    }

}
