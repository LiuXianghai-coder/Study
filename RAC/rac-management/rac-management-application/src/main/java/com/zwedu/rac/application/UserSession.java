package com.zwedu.rac.application;

import com.zwedu.rac.sdk.rdo.StrategyRdo;
import com.zwedu.rac.sdk.rdo.UserRdo;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 用户session
 *
 * @author qingchuan
 * @date 2020/12/24
 */
@Data
@Builder
public class UserSession implements Serializable {
    /**
     * 用户信息
     */
    private UserRdo userRdo;
    /**
     * 功能访问控制集合
     */
    private Map<String, StrategyRdo> funcStrategyMap;
}
