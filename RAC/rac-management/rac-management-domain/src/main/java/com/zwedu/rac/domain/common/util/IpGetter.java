package com.zwedu.rac.domain.common.util;


import com.zwedu.rac.domain.common.constant.SeparationChar;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * ip获取器
 *
 * @author qingchuan
 * @date 2020/12/10
 */
public class IpGetter {
    /**
     * 获取客户端IP
     *
     * @param request HttpServletRequest
     * @return ip地址
     */
    public static String getClientIp(HttpServletRequest request) {
        // 如果用户访问是被代理过的，则从header里面取其地址
        String cip = request.getHeader("clientip");
        if (StringUtils.isEmpty(cip) || null == cip) {
            // 如果用户的访问不是被代理过的，则直接取其ip地址
            cip = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(cip) || null == cip) {
                cip = request.getRemoteAddr();
            }
        }
        if (StringUtils.indexOf(cip, SeparationChar.COMMA) > 0) {
            String[] ipArr = StringUtils.split(cip, SeparationChar.COMMA);
            if (StringUtils.isNotEmpty(ipArr[0])) {
                cip = ipArr[0].trim();
            }
        }
        return cip;
    }
}
