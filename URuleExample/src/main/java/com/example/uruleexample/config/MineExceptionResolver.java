package com.example.uruleexample.config;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MineExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex
    ) {
        if (ex instanceof IllegalArgumentException) {
            ModelAndView view = new ModelAndView();
            view.setStatus(HttpStatus.ACCEPTED);
        }
        return null;
    }
}
