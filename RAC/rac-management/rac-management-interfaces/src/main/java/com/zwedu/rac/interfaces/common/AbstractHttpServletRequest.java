package com.zwedu.rac.interfaces.common;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 抽象http处理
 *
 * @author qingchuan
 * @date 2021/1/10
 */
public interface AbstractHttpServletRequest {
    /**
     * 业务线ID
     */
    Long BIZ_LINE_ID = 1L;
    /**
     * ajax 请求头
     */
    String AJAX_HEADER = "X-Requested-With";
    /**
     * ajax 请求头值
     */
    String AJAX_HEADER_CONTENT = "XMLHttpRequest";
    /**
     * 跳转模板
     */
    String TEMPLATE = "<script type='text/javascript'>(window.parent||window).location.replace('%s');</script>";

    /**
     * 判断是否是ajax请求
     *
     * @param request HttpServletRequest对象
     * @return true 代表是 FALSE 代表否
     */
    default boolean isAjaxRequest(HttpServletRequest request) {
        String requestType = request.getHeader(AJAX_HEADER) == null ? StringUtils.EMPTY :
                request.getHeader(AJAX_HEADER).trim();
        if (StringUtils.isNotEmpty(requestType) && requestType.equals(AJAX_HEADER_CONTENT)) {
            return true;
        }
        return false;
    }

    default void printMessage(HttpServletResponse httpResponse, String template, Object... args) throws IOException {
        PrintWriter writer = httpResponse.getWriter();
        writer.write(String.format(template, args));
        writer.flush();
        writer.close();
    }
}
