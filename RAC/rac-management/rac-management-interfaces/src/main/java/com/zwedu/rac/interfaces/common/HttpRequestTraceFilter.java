package com.zwedu.rac.interfaces.common;

import com.google.common.base.Stopwatch;
import com.zwedu.rac.domain.common.util.IpGetter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 请求时长
 */
@Slf4j
@Order(0)
@Component
@WebFilter(filterName = "httpRequestTraceFilter", urlPatterns = "/*")
public class HttpRequestTraceFilter implements Filter {
    /**
     * 日志跟踪标识
     */
    public static final String TRACE_ID = "TRACE_ID";

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
        MDC.clear();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        MDC.put(TRACE_ID, StringUtils.replace(UUID.randomUUID().toString(), "-", ""));
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            if (stopwatch.isRunning()) {
                stopwatch.stop();
            }
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            log.info("Client-Ip:{}, Request-Url:{}, Content-Type:{}, User-Agent:{}, time-consuming(ms)={}",
                    IpGetter.getClientIp(httpServletRequest),
                    httpServletRequest.getServletPath(),
                    httpServletRequest.getHeader("Content-Type"),
                    httpServletRequest.getHeader("User-Agent"),
                    stopwatch.elapsed(TimeUnit.MILLISECONDS));
        }
    }
}