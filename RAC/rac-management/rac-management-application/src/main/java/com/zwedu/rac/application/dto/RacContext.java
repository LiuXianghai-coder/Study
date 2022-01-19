package com.zwedu.rac.application.dto;

import com.zwedu.rac.application.dto.role.RoleSimpleDto;

import java.util.List;

/**
 * rac用户上线文信息
 *
 * @author qingchuan
 * @date 2020/12/21
 */
public class RacContext {
    /**
     * 当前用户ID
     */
    private Long userId;
    /**
     * 用户角色列表
     */
    private List<RoleSimpleDto> roleList;
}
