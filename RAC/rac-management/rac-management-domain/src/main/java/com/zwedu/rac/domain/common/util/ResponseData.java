package com.zwedu.rac.domain.common.util;

/**
 * 返回结果包含data
 *
 * @author qingchuan
 * @date 2020/12/9
 */
public class ResponseData<T> extends BaseResponse {
    /**
     * 数据
     */
    private T data;

    /**
     * 构造函数
     *
     * @param status 状态
     * @param msg    消息
     * @param data   数据
     */
    public ResponseData(Integer status, String msg, T data) {
        super(status, msg);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultData{" +
                "data=" + data +
                "} " + super.toString();
    }
}