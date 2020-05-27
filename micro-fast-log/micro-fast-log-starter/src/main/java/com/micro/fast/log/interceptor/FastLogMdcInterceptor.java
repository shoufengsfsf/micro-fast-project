package com.micro.fast.log.interceptor;

import com.micro.fast.log.configuration.properties.FastLogProperties;
import com.micro.fast.log.constant.FastLogConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * mdc拦截器
 *
 * @author shoufeng
 */
@Slf4j
@Component
public class FastLogMdcInterceptor implements HandlerInterceptor {

    @Resource
    private FastLogProperties fastLogProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MDC.put(FastLogConstant.REQUEST_METHOD, request.getMethod());
        MDC.put(FastLogConstant.ACCESS_TOKEN, getAccessToken(request));
        MDC.put(FastLogConstant.REQUEST_URI, request.getRequestURI());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.remove(FastLogConstant.REQUEST_METHOD);
        MDC.remove(FastLogConstant.ACCESS_TOKEN);
        MDC.remove(FastLogConstant.REQUEST_URI);
    }

    private String getAccessToken(HttpServletRequest request) {
        String tokenHeader = fastLogProperties.getTokenHeader();
        String accessToken = request.getHeader(tokenHeader);
        if (StringUtils.isNoneBlank(accessToken)) {
            return accessToken;
        }
        Cookie[] cookies = request.getCookies();
        if (ArrayUtils.isEmpty(cookies)) {
            return StringUtils.EMPTY;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase(tokenHeader)) {
                return cookie.getValue();
            }
        }
        return StringUtils.EMPTY;
    }
}
