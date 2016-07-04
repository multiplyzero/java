package com.yiwugou.reb.web.exc;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerMethodExceptionResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 统一异常处理类
 * 
 * @author zhanxiaoyong@yiwugou.com
 * 
 * @since 2016年6月17日 下午4:54:17
 */
public class MyExceptionHandler extends AbstractHandlerMethodExceptionResolver {

    @Override
    protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response,
            HandlerMethod handlerMethod, Exception exception) {
        if (handlerMethod == null) {
            return null;
        }

        Method method = handlerMethod.getMethod();
        if (method == null) {
            return null;
        }

        ResponseBody responseBody = AnnotationUtils.findAnnotation(method, ResponseBody.class);
        if (responseBody != null) {
            try {
                ResponseStatus responseStatus = AnnotationUtils.findAnnotation(method, ResponseStatus.class);
                if (responseStatus != null) {
                    HttpStatus httpStatus = responseStatus.value();
                    String reason = responseStatus.reason();
                    if (StringUtils.hasText(reason)) {
                        response.sendError(httpStatus.value(), reason);
                    } else {
                        response.setStatus(httpStatus.value());
                    }
                } else {
                    Map<String, Object> param = new HashMap<String, Object>();
                    param.put("code", "-1");
                    param.put("msg", exception.getMessage());
                    if (exception instanceof LoginException) {
                        param.put("redirect", "/login.html");
                    }
                    response.setStatus(HttpStatus.OK.value());
                    new ObjectMapper().writeValue(response.getWriter(), param);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        } else {
            ModelAndView mv = new ModelAndView();
            if (exception instanceof IllegalArgumentException) {
                mv.addObject("msg", exception.getMessage());
                mv.setViewName("param_error");
            } else if (exception instanceof LoginException) {
                mv.addObject("msg", exception.getMessage());
                mv.setViewName("login");
            } else {
                mv.addObject("msg", exception.getMessage());
                mv.setViewName("error");
            }

            return mv;
        }

    }
}