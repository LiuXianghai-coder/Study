package com.zwedu.rac.domain.common.util;

import com.zwedu.rac.domain.common.enums.ResponseCodeEnum;

/**
 * 返回结果工具类
 *
 * @author qingchuan
 * @date 2020/12/9
 */
public class ResponseUtil {

    /**
     * success 返回成功结果（带data）
     *
     * @param: data
     * @return:ResultData<T>
     */
    public static <T> ResponseData<T> success(T data) {
        return new ResponseData(ResponseCodeEnum.SUCCESS.getValue(), ResponseCodeEnum.SUCCESS.getText(), data);
    }

    /**
     * success 返回成功结果（不带data）
     *
     * @param:
     * @return: BaseResult
     */
    public static BaseResponse success() {
        return new BaseResponse(ResponseCodeEnum.SUCCESS.getValue(), ResponseCodeEnum.SUCCESS.getText());
    }

    /**
     * fail 返回失败结果
     *
     * @param: msg 失败信息
     * @return: ResultData<T>
     */
    public static BaseResponse fail(String msg) {
        return new BaseResponse(ResponseCodeEnum.FAIL.getValue(), msg);
    }

    /**
     * 返回失败结果
     *
     * @param: resultStatus 错误类型
     * @return: BaseResult
     */
    public static BaseResponse fail(ResponseCodeEnum resultEnum) {
        return new BaseResponse(resultEnum.getValue(), resultEnum.getText());
    }

    /**
     * 返回失败结果(自定义结果)
     *
     * @param: status 错误状态码
     * @param: msg 错误信息
     * @return: BaseResult
     */
    public static BaseResponse fail(Integer status, String msg) {
        return new BaseResponse(status, msg);
    }
}