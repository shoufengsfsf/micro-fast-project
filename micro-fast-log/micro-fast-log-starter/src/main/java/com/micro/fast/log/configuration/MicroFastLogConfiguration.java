package com.micro.fast.log.configuration;

import com.micro.fast.log.aspect.SysLogAspect;
import com.micro.fast.log.configuration.properties.MicroFastLogProperties;
import com.micro.fast.log.interceptor.MicroFastLogMdcInterceptor;
import com.micro.fast.log.listener.SysLogListener;
import com.micro.fast.log.service.SysLogService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 日志配置
 *
 * @author shoufeng
 */

@Configuration
@ConditionalOnBean(value = {SysLogService.class})
@ConditionalOnProperty(prefix = "micro.fast.log", name = "enable", havingValue = "true")
@ComponentScan(value = {"com.micro.fast.log"})
public class MicroFastLogConfiguration implements WebMvcConfigurer {

    @Resource
    private MicroFastLogProperties fastLogProperties;

    @Resource
    private MicroFastLogMdcInterceptor fastLogMdcInterceptor;

    @Resource
    private SysLogService sysLogService;

    @Bean
    public SysLogAspect sysLogAspect() {
        return new SysLogAspect();
    }

    @Bean
    public SysLogListener sysLogListener() {
        return new SysLogListener(sysLogBaseDto -> sysLogService.saveSysLog(sysLogBaseDto));
    }

    /**
     * 注册mdc拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(fastLogMdcInterceptor)
                .addPathPatterns("/**")
                .order(fastLogProperties.getInterceptorOrder());
    }
}
