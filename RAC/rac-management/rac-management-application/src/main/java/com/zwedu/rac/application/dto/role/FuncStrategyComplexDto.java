package com.zwedu.rac.application.dto.role;

/**
 * 功能策略DTO
 *
 * @author qingchuan
 * @date 2020/12/16
 */
public class FuncStrategyComplexDto {
    /**
     * 功能ID列表
     */
    private Long funcId;
    /**
     * 策略ID
     */
    private Long strategyId;
    /**
     * 功能名称
     */
    private String funcName;
    /**
     * 访问控制策略名称
     */
    private String strategyName;

    public Long getFuncId() {
        return funcId;
    }

    public void setFuncId(Long funcId) {
        this.funcId = funcId;
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }
}
