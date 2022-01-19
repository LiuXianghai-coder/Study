package com.zwedu.rac.application.dto.role;

import com.zwedu.rac.application.dto.BaseSimpleDto;

import java.util.List;

/**
 * 业务线传输对象
 *
 * @author qingchuan
 * @date 2020/12/9
 */
public class RoleSimpleDto extends BaseSimpleDto {
    /**
     * 功能策略列表
     */
    private List<Long> funcIds;
    /**
     * 策略ID
     */
    private Long strategyId;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 菜单ID列表
     */
    private List<Long> menuIds;

    public List<Long> getFuncIds() {
        return funcIds;
    }

    public void setFuncIds(List<Long> funcIds) {
        this.funcIds = funcIds;
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

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
