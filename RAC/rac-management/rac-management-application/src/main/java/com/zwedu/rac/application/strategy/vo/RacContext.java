package com.zwedu.rac.application.strategy.vo;

import com.zwedu.rac.sdk.rdo.UserRdo;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * 上线文信息
 *
 * @author qingchuan
 * @date 2020/12/24
 */
@Data
@Builder
public class RacContext {
    /**
     * 用户ID
     */
    private UserRdo userRdo;
    /**
     * 参数对象
     */
    private Map<String, Object> paramMap;
}
