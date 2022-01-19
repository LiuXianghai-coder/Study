package com.zwedu.rac.application.dto.role;

import java.util.List;

/**
 * 角色菜单DTO
 *
 * @author qingchuan
 * @date 2020/12/16
 */
public class RoleMenuDto {
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 菜单ID列表
     */
    private List<Long> menuIds;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<Long> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<Long> menuIds) {
        this.menuIds = menuIds;
    }
}
