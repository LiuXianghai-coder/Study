package com.zwedu.rac.interfaces.controller;

import com.zwedu.rac.application.dto.ReqPaginationDto;
import com.zwedu.rac.application.dto.ext.ExtPropertySimpleDto;
import com.zwedu.rac.application.service.ExtPropertyAppService;
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
 * 扩展属性控制器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Controller
@Slf4j
@RequestMapping("/property")
public class ExtPropertyController {
    @Resource
    private ExtPropertyAppService extPropertyAppService;

    /**
     * 查询可用的扩展属性列表
     *
     * @param paginationDto 分页查询参数
     * @return 扩展属性列表数据
     */
    @RequestMapping("/listPage")
    @ResponseBody
    public BaseResponse listPage(@RequestBody ReqPaginationDto paginationDto) {
        return ResponseUtil.success(extPropertyAppService.listPage(paginationDto));
    }


    /**
     * 查询扩展属性列表
     *
     * @return 扩展属性列表数据
     */
    @RequestMapping("/listByBizLineId")
    @ResponseBody
    public BaseResponse listByBizLineId(@RequestBody ExtPropertySimpleDto record) {
        return ResponseUtil.success(extPropertyAppService.listByBizLineId(record));
    }

    /**
     * 查询扩展属性列表
     *
     * @return 扩展属性列表数据
     */
    @RequestMapping("/listByBizEntityEnName")
    @ResponseBody
    public BaseResponse listByBizEntityEnName(@RequestBody ExtPropertySimpleDto record) {
        return ResponseUtil.success(extPropertyAppService.listByBizEntityEnName(record));
    }

    /**
     * 创建扩展属性
     *
     * @param record 扩展属性数据
     */
    @RequestMapping("/create")
    @ResponseBody
    public BaseResponse create(@RequestBody ExtPropertySimpleDto record) {
        Long currentLoginId = SessionHelper.getLoginUserId();
        extPropertyAppService.create(currentLoginId, record);
        return ResponseUtil.success();
    }


    /**
     * 更新扩展属性
     *
     * @param record 扩展属性数据
     */
    @RequestMapping("/edit")
    @ResponseBody
    public BaseResponse edit(@RequestBody ExtPropertySimpleDto record) {
        Long currentLoginId = SessionHelper.getLoginUserId();
        extPropertyAppService.edit(currentLoginId, record);
        return ResponseUtil.success();
    }


    /**
     * 删除扩展属性
     *
     * @param record 扩展属性
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResponse delete(@RequestBody ExtPropertySimpleDto record) {
        Long currentLoginId = SessionHelper.getLoginUserId();
        extPropertyAppService.delete(currentLoginId, record);
        return ResponseUtil.success();
    }
}
