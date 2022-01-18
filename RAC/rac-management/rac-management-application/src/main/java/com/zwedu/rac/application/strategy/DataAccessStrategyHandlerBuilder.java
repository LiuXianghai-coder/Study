package com.zwedu.rac.application.strategy;

import com.google.common.collect.ImmutableMap;
import com.zwedu.rac.application.strategy.impl.AllAccessStrategyHandler;
import com.zwedu.rac.application.strategy.impl.UserExtPropertyAccessStrategyHandler;
import com.zwedu.rac.application.strategy.impl.UserPropertyAccessStrategyHandler;
import com.zwedu.rac.domain.common.constant.SeparationChar;
import com.zwedu.rac.domain.common.enums.PropertyTypeEnum;
import com.zwedu.rac.domain.common.enums.StrategyTypeEnum;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 数据访问策略处理器类
 *
 * @author qingchuan
 * @date 2020/12/24
 */
@Component
public class DataAccessStrategyHandlerBuilder {
    /**
     * 持有器
     */
    private static final Map<String, DataAccessStrategyHandler> HOLDER = ImmutableMap.of(
            StrategyTypeEnum.ALL.getText(), new AllAccessStrategyHandler(),
            StrategyTypeEnum.USER_PROPERTY.getText(), new UserPropertyAccessStrategyHandler(),
            getKey(StrategyTypeEnum.USER_PROPERTY.getText(), PropertyTypeEnum.EXT.getText()),
            new UserExtPropertyAccessStrategyHandler()
    );

    /**
     * 获取实例
     *
     * @return 策略表达式处理器
     */
    public static DataAccessStrategyHandler instance(String type) {
        if (!HOLDER.containsKey(type)) {
            throw new IllegalArgumentException(String.format("can't find strategy handler for type=%s", type));
        }
        return HOLDER.get(type);
    }


    /**
     * 获取实例
     *
     * @return 策略表达式处理器
     */
    public static DataAccessStrategyHandler instance(String strategyName, String type) {
        String key = getKey(strategyName, type);
        if (!HOLDER.containsKey(key)) {
            throw new IllegalArgumentException(String.format("can't find strategy handler for type=%s", type));
        }
        return HOLDER.get(key);
    }

    /**
     * 获取策略对应的解析器key
     *
     * @param strategyName 策略名
     * @param type         属性类型
     * @return key
     */
    private static String getKey(String strategyName, String type) {
        return strategyName + SeparationChar.UNDERLINE + type;
    }
}
