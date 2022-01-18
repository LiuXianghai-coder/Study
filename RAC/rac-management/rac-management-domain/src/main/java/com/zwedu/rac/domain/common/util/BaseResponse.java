package com.zwedu.rac.domain.common.util;

/**
 * 返回结果基类
 *
 * @author qingchuan
 * @date 2020/12/9
 */
public class BaseResponse {
    /**
     * 状态
     */
    private Integer status;
    /**
     * 消息
     */
    private String msg;

    public BaseResponse() {
    }

    public BaseResponse(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "BaseResult{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}