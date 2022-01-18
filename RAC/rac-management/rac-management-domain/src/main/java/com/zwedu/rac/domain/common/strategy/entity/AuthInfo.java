package com.zwedu.rac.domain.common.strategy.entity;


import com.zwedu.rac.domain.common.enums.DataAccessEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 授权信息
 *
 * @author qingchuan
 * @date 2020/12/24
 */
@Data
@Builder
public class AuthInfo {
    /**
     * 授权类型
     */
    private DataAccessEnum dataAccess;
    /**
     * 是否有所有数据管辖权限
     */
    private String fieldName;
    /**
     * 数据库字典名
     */
    private String dbFieldName;
    /**
     * 授权列表数据
     */
    private List<String> authList;
}
