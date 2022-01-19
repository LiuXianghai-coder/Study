package com.zwedu.rac.interfaces.controller;

import com.zwedu.rac.application.dto.dimension.DimensionNodeComplexDto;
import com.zwedu.rac.application.dto.dimension.DimensionNodeSimpleDto;
import com.zwedu.rac.application.dto.ext.ExtDataSimpleDto;
import com.zwedu.rac.application.service.DimensionNodeAppService;
import com.zwedu.rac.domain.common.util.BaseResponse;
import com.zwedu.rac.domain.common.util.ResponseData;
import com.zwedu.rac.domain.common.util.ResponseUtil;
import com.zwedu.rac.interfaces.common.SessionHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 维度节点控制器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Controller
@Slf4j
@RequestMapping("/dimension/node")
public class DimensionNodeController {
    @Resource
    private DimensionNodeAppService dimensionNodeAppService;

    /**
     * 查询维度节点列表
     *
     * @param record 查询参数
     * @return 维度节点列表数据
     */
    @RequestMapping("/listByParentId")
    @ResponseBody
    public BaseResponse listByParentId(@RequestBody DimensionNodeSimpleDto record) {
        return ResponseUtil.success(dimensionNodeAppService.listByParentId(record));
    }


    /**
     * 查询维度节点列表
     *
     * @param record 查询参数
     * @return 维度节点列表数据
     */
    @RequestMapping("/listByDimensionId")
    @ResponseBody
    public BaseResponse listByDimensionId(@RequestBody DimensionNodeSimpleDto record) {
        return ResponseUtil.success(dimensionNodeAppService.listByDimensionId(record));
    }


    /**
     * 查询维度的扩展属性
     *
     * @param record 用户扩展属性数据
     */
    @RequestMapping("/listExtProperty")
    @ResponseBody
    public BaseResponse listExtProperty(@RequestBody DimensionNodeSimpleDto record) {
        return ResponseUtil.success(dimensionNodeAppService.listExtProperty(record));
    }



    /**
     * 添加用户扩展属性
     *
     * @param record 用户扩展属性数据
     */
    @RequestMapping("/addExtProperty")
    @ResponseBody
    public BaseResponse addExtProperty(@RequestBody ExtDataSimpleDto record) {
        Long currentLoginId = SessionHelper.getLoginUserId();
        dimensionNodeAppService.addExtProperty(currentLoginId, record);
        return ResponseUtil.success();
    }


    /**
     * 删除用户扩展属性
     *
     * @param record 用户扩展属性数据
     */
    @RequestMapping("/dropExtProperty")
    @ResponseBody
    public BaseResponse dropExtProperty(@RequestBody ExtDataSimpleDto record) {
        dimensionNodeAppService.dropExtProperty(record);
        return ResponseUtil.success();
    }

    /**
     * 创建维度节点
     *
     * @param record 维度节点数据
     */
    @RequestMapping("/create")
    @ResponseBody
    public BaseResponse create(@RequestBody DimensionNodeSimpleDto record) {
        Long currentLoginId = SessionHelper.getLoginUserId();
        dimensionNodeAppService.create(currentLoginId, record);
        return ResponseUtil.success();
    }


    /**
     * 创建维度节点
     *
     * @param record 维度节点数据
     */
    @RequestMapping("/bindObjectNode")
    @ResponseBody
    public BaseResponse bindObjectNode(@RequestBody DimensionNodeSimpleDto record) {
        Long currentLoginId = SessionHelper.getLoginUserId();
        dimensionNodeAppService.bindObjectNode(currentLoginId, record);
        return ResponseUtil.success();
    }

    /**
     * 修改维度节点
     *
     * @param record 维度节点数据
     */
    @RequestMapping("/unbindObjectNode")
    @ResponseBody
    public BaseResponse unbindObjectNode(@RequestBody DimensionNodeSimpleDto record) {
        Long currentLoginId = SessionHelper.getLoginUserId();
        dimensionNodeAppService.unbindObjectNode(currentLoginId, record);
        return ResponseUtil.success();
    }

    /**
     * 查询客体维度节点
     *
     * @param record 维度节点数据
     */
    @RequestMapping("/queryObjectNode")
    @ResponseBody
    public ResponseData<DimensionNodeComplexDto> queryObjectNode(@RequestBody DimensionNodeSimpleDto record) {
        return ResponseUtil.success(dimensionNodeAppService.queryObjectNode(record));
    }

    /**
     * 更新维度节点
     *
     * @param record 维度节点数据
     */
    @RequestMapping("/edit")
    @ResponseBody
    public BaseResponse edit(@RequestBody DimensionNodeSimpleDto record) {
        Long currentLoginId = SessionHelper.getLoginUserId();
        dimensionNodeAppService.edit(currentLoginId, record);
        return ResponseUtil.success();
    }


    /**
     * 删除维度节点
     *
     * @param record 维度节点
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResponse delete(@RequestBody DimensionNodeSimpleDto record) {
        Long currentLoginId = SessionHelper.getLoginUserId();
        dimensionNodeAppService.delete(currentLoginId, record);
        return ResponseUtil.success();
    }
}
