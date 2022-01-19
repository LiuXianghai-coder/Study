package com.zwedu.cust.controller;

import com.zwedu.cust.entity.Cust;
import com.zwedu.cust.service.CustService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 客户控制器
 *
 * @author qingchuan
 * @date 2020/12/12
 */
@Slf4j
@Controller
@RequestMapping("cust")
public class CustController {

    @Resource
    private CustService custService;

    /**
     * 客户列表
     * @return
     */
    @RequestMapping("/listByUser")
    @ResponseBody
    public List<Cust> listByUser(Long userId) {
        return custService.listCust(userId);
    }
}
