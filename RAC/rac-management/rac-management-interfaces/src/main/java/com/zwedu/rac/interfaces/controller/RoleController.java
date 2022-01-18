package com.zwedu.rac.interfaces.controller;

import com.zwedu.rac.application.dto.ReqPaginationDto;
import com.zwedu.rac.application.dto.role.RoleSimpleDto;
import com.zwedu.rac.application.service.RoleAppService;
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
 * 角色控制器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Controller
@Slf4j
@RequestMapping("/role")
public class RoleController {
    @Resource
    private RoleAppService roleAppService;

    /**
     * 查询可用的角色列表
     *
     * @param record 分页查询参数
     * @return 角色列表数据
     */
    @RequestMapping("/listPage")
    @ResponseBody
    public BaseResponse listPage(@RequestBody ReqPaginationDto record) {
        return ResponseUtil.success(roleAppService.listPage(record));
    }

    /**
     * 查询授权的角色列表
     *
     * @return 角色列表数据
     */
    @RequestMapping("/listByBizLineId")
    @ResponseBody
    public BaseResponse listByBizLineId(@RequestBody RoleSimpleDto record) {
        return ResponseUtil.success(roleAppService.listByBizLineId(record));
    }

    /**
     * 查询角色绑定菜单ID
     *
     * @param record 用户角色数据
     */
    @RequestMapping("/listBindMenuId")
    @ResponseBody
    public BaseResponse listBindMenuId(@RequestBody RoleSimpleDto record) {
        return ResponseUtil.success(roleAppService.listBindMenuId(record));
    }

    /**
     * 查询角色绑定功能ID
     *
     * @param record 用户角色数据
     */
    @RequestMapping("/listAuth")
    @ResponseBody
    public BaseResponse listAuth(@RequestBody ReqPaginationDto record) {
        return ResponseUtil.success(roleAppService.listAuth(record));
    }

    /**
     * 绑定功能
     *
     * @param record 角色功能数据
     */
    @RequestMapping("/bindAuth")
    @ResponseBody
    public BaseResponse bindAuth(@RequestBody RoleSimpleDto record) {
        Long currentLoginId = SessionHelper.getLoginUserId();
        roleAppService.bindAuth(currentLoginId, record);
        return ResponseUtil.success();
    }

    /**
     * 绑定菜单
     *
     * @param record 角色菜单数据
     */
    @RequestMapping("/bindMenu")
    @ResponseBody
    public BaseResponse bindMenu(@RequestBody RoleSimpleDto record) {
        Long currentLoginId = SessionHelper.getLoginUserId();
        roleAppService.bindMenu(currentLoginId, record);
        return ResponseUtil.success();
    }



    /**
     * 解绑
     *
     * @param record 角色解绑功能数据
     */
    @RequestMapping("/unbindAuth")
    @ResponseBody
    public BaseResponse unbindAuth(@RequestBody RoleSimpleDto record) {
        roleAppService.unbindAuth(record);
        return ResponseUtil.success();
    }

    /**
     * 创建角色
     *
     * @param record 角色数据
     */
    @RequestMapping("/create")
    @ResponseBody
    public BaseResponse create(@RequestBody RoleSimpleDto record) {
        Long currentLoginId = SessionHelper.getLoginUserId();
        roleAppService.create(currentLoginId, record);
        return ResponseUtil.success();
    }


    /**
     * 更新角色
     *
     * @param record 角色数据
     */
    @RequestMapping("/edit")
    @ResponseBody
    public BaseResponse edit(@RequestBody RoleSimpleDto record) {
        Long currentLoginId = SessionHelper.getLoginUserId();
        roleAppService.edit(currentLoginId, record);
        return ResponseUtil.success();
    }


    /**
     * 删除角色
     *
     * @param record 角色信息
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResponse delete(@RequestBody RoleSimpleDto record) {
        Long currentLoginId = SessionHelper.getLoginUserId();
        roleAppService.delete(currentLoginId, record);
        return ResponseUtil.success();
    }
}
