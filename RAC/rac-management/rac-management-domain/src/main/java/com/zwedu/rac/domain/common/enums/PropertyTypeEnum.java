package com.zwedu.rac.domain.common.enums;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * 用户状态枚举
 *
 * @author qingchuan
 * @date 2020/12/11
 */
@Getter
@AllArgsConstructor
public enum PropertyTypeEnum {

    BASIC("Basic", "Basic"), EXT("Ext", "Ext");

    /**
     * 类型
     */
    private String value;
    /**
     * 描述
     */
    private String text;
    /**
     * 枚举map
     */
    public static final Map<String, PropertyTypeEnum> VALUE_TEXT = Maps.newHashMap();

    /**
     * 静态初始化
     */
    static {
        for (PropertyTypeEnum item : values()) {
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
    public static PropertyTypeEnum of(String value) {
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
    public static boolean hasEnum(String value) {
        return VALUE_TEXT.containsKey(value);
    }

    /**
     * 是否是扩展类型
     *
     * @param value 值
     * @return true 是，false 否
     */
    public static boolean isExt(String value) {
        return of(value).equals(EXT);
    }
}
