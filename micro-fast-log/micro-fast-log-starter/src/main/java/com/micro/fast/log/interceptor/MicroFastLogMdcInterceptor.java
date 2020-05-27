package com.micro.fast.log.interceptor;

import com.micro.fast.log.configuration.properties.MicroFastLogProperties;
import com.micro.fast.log.constant.MicroFastLogConstant;
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
public class MicroFastLogMdcInterceptor implements HandlerInterceptor {

    @Resource
    private MicroFastLogProperties fastLogProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MDC.put(MicroFastLogConstant.REQUEST_METHOD, request.getMethod());
        MDC.put(MicroFastLogConstant.ACCESS_TOKEN, getAccessToken(request));
        MDC.put(MicroFastLogConstant.REQUEST_URI, request.getRequestURI());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.remove(MicroFastLogConstant.REQUEST_METHOD);
        MDC.remove(MicroFastLogConstant.ACCESS_TOKEN);
        MDC.remove(MicroFastLogConstant.REQUEST_URI);
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
