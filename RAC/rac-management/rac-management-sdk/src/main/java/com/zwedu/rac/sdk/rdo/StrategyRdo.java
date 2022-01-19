package com.zwedu.rac.sdk.rdo;

import java.io.Serializable;

/**
 * 访问控制策略
 *
 * @author qingchuan
 * @date 2020/12/21
 */
public class StrategyRdo implements Serializable {
    /**
     * 策略类型
     */
    private Integer type;
    /**
     * 策略表达式
     */
    private String expression;


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

}
