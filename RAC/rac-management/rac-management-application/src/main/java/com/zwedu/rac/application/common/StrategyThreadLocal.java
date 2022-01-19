package com.zwedu.rac.application.common;

import com.zwedu.rac.sdk.rdo.StrategyRdo;

/**
 * 当前策略threadlocal
 *
 * @author qingchuan
 * @date 2020/12/25
 */
public interface StrategyThreadLocal {
    /**
     * 当前策略
     */
    ThreadLocal<StrategyRdo> CURRENT_STRATEGY = new ThreadLocal<>();
}
