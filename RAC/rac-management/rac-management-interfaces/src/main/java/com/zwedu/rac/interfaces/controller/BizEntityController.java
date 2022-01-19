package com.zwedu.rac.interfaces.controller;

import com.zwedu.rac.application.dto.ReqPaginationDto;
import com.zwedu.rac.application.dto.bizentity.BizEntitySimpleDto;
import com.zwedu.rac.application.service.BizEntityAppService;
import com.zwedu.rac.domain.common.util.BaseResponse;
import com.zwedu.rac.domain.common.util.ResponseUtil;
import com.zwedu.rac.interfaces.common.SessionHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 业务实体控制器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Controller
@Slf4j
@RequestMapping("/bizentity")
public class BizEntityController {
    @Resource
    private BizEntityAppService bizEntityAppService;

    /**
     * 查询可用的业务实体列表
     *
     * @param paginationDto 分页查询参数
     * @return 业务实体列表数据
     */
    @RequestMapping("/listPage")
    @ResponseBody
    public BaseResponse listPage(@RequestBody ReqPaginationDto paginationDto) {
        return ResponseUtil.success(bizEntityAppService.listPage(paginationDto));
    }


    /**
     * 查询业务实体列表
     *
     * @return 业务实体列表数据
     */
    @RequestMapping("/listByBizLineId")
    @ResponseBody
    public BaseResponse listByBizLineId(@RequestBody BizEntitySimpleDto record) {
        return ResponseUtil.success(bizEntityAppService.listByBizLineId(record));
    }

    /**
     * 创建业务实体
     *
     * @param record 业务实体数据
     */
    @RequestMapping("/create")
    @ResponseBody
    public BaseResponse create(@RequestBody BizEntitySimpleDto record) {
        Long currentLoginId = SessionHelper.getLoginUserId();
        bizEntityAppService.create(currentLoginId, record);
        return ResponseUtil.success();
    }


    /**
     * 更新业务实体
     *
     * @param record 业务实体数据
     */
    @RequestMapping("/edit")
    @ResponseBody
    public BaseResponse edit(@RequestBody BizEntitySimpleDto record) {
        Long currentLoginId = SessionHelper.getLoginUserId();
        bizEntityAppService.edit(currentLoginId, record);
        return ResponseUtil.success();
    }


    /**
     * 删除业务实体
     *
     * @param record 业务实体
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResponse delete(@RequestBody BizEntitySimpleDto record) {
        Long currentLoginId = SessionHelper.getLoginUserId();
        bizEntityAppService.delete(currentLoginId, record);
        return ResponseUtil.success();
    }
}
