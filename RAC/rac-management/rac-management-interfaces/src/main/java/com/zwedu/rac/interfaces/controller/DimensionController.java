package com.zwedu.rac.interfaces.controller;

import com.zwedu.rac.application.dto.ReqPaginationDto;
import com.zwedu.rac.application.dto.dimension.DimensionSimpleDto;
import com.zwedu.rac.application.service.DimensionAppService;
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
 * 维度控制器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Controller
@Slf4j
@RequestMapping("/dimension")
public class DimensionController {
    @Resource
    private DimensionAppService dimensionAppService;

    /**
     * 查询可用的维度列表
     *
     * @param paginationDto 分页查询参数
     * @return 维度列表数据
     */
    @RequestMapping("/listPage")
    @ResponseBody
    public BaseResponse listPage(@RequestBody ReqPaginationDto paginationDto) {
        return ResponseUtil.success(dimensionAppService.listPage(paginationDto));
    }


    /**
     * 查询维度列表
     *
     * @return 维度列表数据
     */
    @RequestMapping("/listByBizLineId")
    @ResponseBody
    public BaseResponse listByBizLineId(@RequestBody DimensionSimpleDto record) {
        return ResponseUtil.success(dimensionAppService.listByBizLineId(record));
    }



    /**
     * 查询维度详情
     *
     * @return 维度数据
     */
    @RequestMapping("/queryByEnName")
    @ResponseBody
    public BaseResponse queryByEnName(@RequestBody DimensionSimpleDto record) {
        return ResponseUtil.success(dimensionAppService.queryByEnName(record));
    }

    /**
     * 创建维度
     *
     * @param record 维度数据
     */
    @RequestMapping("/create")
    @ResponseBody
    public BaseResponse create(@RequestBody DimensionSimpleDto record) {
        Long currentLoginId = SessionHelper.getLoginUserId();
        dimensionAppService.create(currentLoginId, record);
        return ResponseUtil.success();
    }


    /**
     * 更新维度
     *
     * @param record 维度数据
     */
    @RequestMapping("/edit")
    @ResponseBody
    public BaseResponse edit(@RequestBody DimensionSimpleDto record) {
        Long currentLoginId = SessionHelper.getLoginUserId();
        dimensionAppService.edit(currentLoginId, record);
        return ResponseUtil.success();
    }


    /**
     * 删除维度
     *
     * @param record 维度
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResponse delete(@RequestBody DimensionSimpleDto record) {
        Long currentLoginId = SessionHelper.getLoginUserId();
        dimensionAppService.delete(currentLoginId, record);
        return ResponseUtil.success();
    }
}
