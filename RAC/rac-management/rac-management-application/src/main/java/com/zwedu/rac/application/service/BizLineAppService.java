package com.zwedu.rac.application.service;

import com.zwedu.rac.application.converter.BizLineEntity2ComplexDtoConverter;
import com.zwedu.rac.application.converter.BizLineEntity2SimpleDtoConverter;
import com.zwedu.rac.application.converter.BizLineSimpleDto2EntityConverter;
import com.zwedu.rac.application.dto.ReqPaginationDto;
import com.zwedu.rac.application.dto.ResPaginationDto;
import com.zwedu.rac.application.dto.bizline.BizLineComplexDto;
import com.zwedu.rac.application.dto.bizline.BizLineSimpleDto;
import com.zwedu.rac.domain.common.annotation.ReadAuth;
import com.zwedu.rac.domain.common.annotation.WriteAuth;
import com.zwedu.rac.domain.common.page.Pagination;
import com.zwedu.rac.domain.common.validator.ParamAssert;
import com.zwedu.rac.domain.entity.BizLineEntity;
import com.zwedu.rac.domain.service.BizLineDomainService;
import com.zwedu.rac.domain.service.UserDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务线应用层服务
 *
 * @author qingchuan
 * @date 2020/12/10
 */
@Service
@Slf4j
public class BizLineAppService {
    @Resource
    private BizLineDomainService bizLineService;
    @Resource
    private UserDomainService userService;

    /**
     * 查询业务线列表数据
     *
     * @param record 分页查询参数
     * @return 业务线列表数据
     */
    @ReadAuth
    public ResPaginationDto<BizLineComplexDto> listPage(ReqPaginationDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        // 查询对应的业务线列表
        Pagination<BizLineEntity> pagination = bizLineService.listPage(record.getPageNo(),
                record.getPageSize(), record.getSearchVal());
        return BizLineEntity2ComplexDtoConverter.INSTANCE.toPaginationDto(pagination);
    }

    /**
     * 查询授权的业务线列表
     *
     * @return 业务线列表数据
     */
    @ReadAuth
    public List<BizLineSimpleDto> listAuthBizLine() {
        return BizLineEntity2SimpleDtoConverter.INSTANCE.toDtoList(bizLineService.listAuthBizLine());
    }

    /**
     * 创建业务线
     *
     * @param currentUserId 登录用户ID
     * @param record        业务线实体
     */
    @WriteAuth
    public void create(Long currentUserId, BizLineSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        bizLineService.create(currentUserId, BizLineSimpleDto2EntityConverter.INSTANCE.toEntity(record));
    }



    /**
     * 更新业务线
     *
     * @param currentUserId 登录用户ID
     * @param record        业务线实体
     */
    @WriteAuth
    public void edit(Long currentUserId, BizLineSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        bizLineService.edit(currentUserId, BizLineSimpleDto2EntityConverter.INSTANCE.toEntity(record));
    }


    /**
     * 删除业务线
     *
     * @param currentUserId 登录用户ID
     * @param record        记录数据
     */
    @WriteAuth
    public void delete(Long currentUserId, BizLineSimpleDto record) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(record);
        bizLineService.delete(currentUserId, record.getId());
    }


}
