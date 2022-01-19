package com.zwedu.rac.interfaces.controller;

import com.zwedu.rac.application.dto.ReqPaginationDto;
import com.zwedu.rac.application.dto.dictionary.DictionarySimpleDto;
import com.zwedu.rac.application.service.DictionaryAppService;
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
 * 字典控制器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Controller
@Slf4j
@RequestMapping("/dictionary")
public class DictionaryController {
    @Resource
    private DictionaryAppService dictionaryAppService;

    /**
     * 查询可用的字典列表
     *
     * @param paginationDto 分页查询参数
     * @return 字典列表数据
     */
    @RequestMapping("/listPage")
    @ResponseBody
    public BaseResponse listPage(@RequestBody ReqPaginationDto paginationDto) {
        return ResponseUtil.success(dictionaryAppService.listPage(paginationDto));
    }


    /**
     * 查询字典列表
     *
     * @return 字典列表数据
     */
    @RequestMapping("/listByBizLineId")
    @ResponseBody
    public BaseResponse listByBizLineId(@RequestBody DictionarySimpleDto record) {
        return ResponseUtil.success(dictionaryAppService.listByBizLineId(record));
    }



    /**
     * 查询维度详情
     *
     * @return 维度数据
     */
    @RequestMapping("/queryByEnName")
    @ResponseBody
    public BaseResponse queryByEnName(@RequestBody DictionarySimpleDto record) {
        return ResponseUtil.success(dictionaryAppService.queryByEnName(record));
    }

    /**
     * 创建字典
     *
     * @param record 字典数据
     */
    @RequestMapping("/create")
    @ResponseBody
    public BaseResponse create(@RequestBody DictionarySimpleDto record) {
        Long currentLoginId = SessionHelper.getLoginUserId();
        dictionaryAppService.create(currentLoginId, record);
        return ResponseUtil.success();
    }


    /**
     * 更新字典
     *
     * @param record 字典数据
     */
    @RequestMapping("/edit")
    @ResponseBody
    public BaseResponse edit(@RequestBody DictionarySimpleDto record) {
        Long currentLoginId = SessionHelper.getLoginUserId();
        dictionaryAppService.edit(currentLoginId, record);
        return ResponseUtil.success();
    }


    /**
     * 删除字典
     *
     * @param record 字典
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResponse delete(@RequestBody DictionarySimpleDto record) {
        Long currentLoginId = SessionHelper.getLoginUserId();
        dictionaryAppService.delete(currentLoginId, record);
        return ResponseUtil.success();
    }
}
