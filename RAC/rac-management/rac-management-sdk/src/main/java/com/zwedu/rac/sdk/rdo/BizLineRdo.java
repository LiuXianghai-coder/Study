package com.zwedu.rac.sdk.rdo;

import java.util.List;

/**
 * 业务线
 *
 * @author qingchuan
 * @date 2020/12/21
 */
public class BizLineRdo {
    /**
     * 业务线ID
     */
    private Long id;
    /**
     * 英文名
     */
    private String enName;
    /**
     * 中文名
     */
    private String cnName;
    /**
     * 角色列表
     */
    private List<RoleRdo> roleList;
}
