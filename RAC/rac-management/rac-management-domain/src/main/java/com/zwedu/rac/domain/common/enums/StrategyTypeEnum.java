package com.zwedu.rac.domain.common.enums;

import com.google.common.collect.Maps;
import com.zwedu.rac.domain.common.constant.SeparationChar;
import com.zwedu.rac.domain.common.strategy.StrategyTypeParser;
import com.zwedu.rac.domain.common.strategy.entity.AllStrategyInfo;
import com.zwedu.rac.domain.common.strategy.entity.FixStrategyInfo;
import com.zwedu.rac.domain.common.strategy.entity.UserPropertyStrategyInfo;
import com.zwedu.rac.domain.common.util.Collections2;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 性别枚举
 *
 * @author qingchuan
 * @date 2020/12/11
 */
@Getter
@Slf4j
@AllArgsConstructor
public enum StrategyTypeEnum {
    ALL(1, "All", "#All[]", expression -> new AllStrategyInfo()),
    USER_PROPERTY(2, "UserProperty", "#UserProperty\\[(\\w+?),(\\w+?),(\\w+?)\\[(\\w+?),(\\w+?)\\]\\]",
            expression -> {
                Pattern pattern = Pattern.compile("#UserProperty\\[(\\w+?),(\\w+?),(\\w+?)\\[(\\w+?),(\\w+?)\\]\\]");
                Matcher matcher = pattern.matcher(expression);
                if (matcher.find()) {
                    return new UserPropertyStrategyInfo().propertyType(matcher.group(1))
                            .propertyName(matcher.group(2)).entityEnName(matcher.group(3))
                            .entityPropertyType(matcher.group(4))
                            .entityPropertyName(matcher.group(5));
                }
                return new UserPropertyStrategyInfo();
            }),
    DIMENSION(3, "Dimension", "#Dimension\\[(\\w+?),(\\w+?)\\]", expression -> new AllStrategyInfo()),
    FIX(4, "Fix", "#Fix\\[(\\w+?)\\]", expression -> {
        Pattern pattern = Pattern.compile("#Dimension\\[(\\w+?),(\\w+?)\\]");
        Matcher matcher = pattern.matcher(expression);
        if (matcher.find()) {
            // 获取用户属性类型
            String values = matcher.group(1);
            // 扩展属性的话，从扩展属性数据中获取
            if (StringUtils.isNotEmpty(values)) {
                return new FixStrategyInfo(Collections2.toStringList(values, SeparationChar.COMMA));
            }
        }
        log.error("not match strategy expression={}", expression);
        return new FixStrategyInfo();
    });

    /**
     * 类型
     */
    private Integer value;
    /**
     * 描述
     */
    private String text;
    /**
     * 策略表达式前缀
     */
    private String pattern;
    /**
     * 解析器
     */
    private StrategyTypeParser parser;
    /**
     * 枚举map
     */
    public static final Map<Integer, StrategyTypeEnum> VALUE_TEXT = Maps.newHashMap();

    /**
     * 静态初始化
     */
    static {
        for (StrategyTypeEnum item : values()) {
            VALUE_TEXT.put(item.getValue(), item);
        }
    }

    /**
     * 根据值获取对应的枚举信息
     *
     * @param value 枚举值
     * @return 枚举信息
     * @throws IllegalArgumentException 枚举值不存在抛出异常
     */
    public static StrategyTypeEnum of(Integer value) {
        if (!VALUE_TEXT.containsKey(value)) {
            throw new IllegalArgumentException(String
                    .format("can't parse enum value %s, because it doesn't existed", value));
        }
        return VALUE_TEXT.get(value);
    }

    /**
     * 枚举值是否存在
     *
     * @param value 枚举值
     * @return true 存在 ，false 不存在
     */
    public static boolean hasEnum(Integer value) {
        return VALUE_TEXT.containsKey(value);
    }
}
