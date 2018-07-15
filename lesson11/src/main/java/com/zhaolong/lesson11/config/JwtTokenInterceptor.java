package com.zhaolong.lesson11.config;

import com.zhaolong.lesson11.util.JwtUtil;
import io.jsonwebtoken.SignatureException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //自动排除生成token的路径,并且如果是options请求是cors跨域预请求，设置allow对应头信息
        if (request.getRequestURI().equals("/api/token") || RequestMethod.OPTIONS.toString().equals(request.getMethod())) {
            return true;
        }

        try {
//            //如果没有header信息
//            if (authHeader == null || authHeader.trim() == "") {
//                throw new SignatureException("not found Authorization.");
//            }

            JwtUtil.validateTokenAddUserIdToHeader(request);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
