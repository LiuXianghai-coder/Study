package com.zwedu.rac.sdk.rdo;

import java.util.List;
import java.util.Map;

/**
 * 角色对象
 *
 * @author qingchuan
 * @date 2020/12/21
 */
public class RoleRdo {
    /**
     * 角色ID
     */
    private Long id;
    /**
     * 角色英文名
     */
    private String enName;
    private String cnName;
    /**
     * 角色对应的功能访问控制集合
     */
    private Map<FuncRdo, StrategyRdo> funcStrategyMap;
    /**
     * 菜单列表
     */
    private List<MenuRdo> menuList;
}
