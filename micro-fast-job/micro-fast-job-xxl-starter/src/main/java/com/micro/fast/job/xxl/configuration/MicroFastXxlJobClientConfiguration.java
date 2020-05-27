package com.micro.fast.job.xxl.configuration;

import com.micro.fast.job.xxl.configuration.properties.MicroFastXxlJobClientProperties;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

/**
 * xxl-job配置文件
 *
 * @author shoufeng
 */
@Configuration
@ConditionalOnProperty(prefix = "micro.fast.xxljob", value = "enable", havingValue = "true")
@ComponentScan(value = {"com.micro.fast.job.xxl.configuration", "com.micro.fast.job.xxl.service"})
@Slf4j
public class MicroFastXxlJobClientConfiguration {

    @Resource
    private MicroFastXxlJobClientProperties microFastXxlJobClientProperties;

    @Resource
    private Environment environment;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        log.info(">>>>>>>>>>> xxl-job config init.");
        String appname = microFastXxlJobClientProperties.getAppname();
        if (StringUtils.isBlank(appname)) {
            appname = environment.getProperty("spring.application.name");
            microFastXxlJobClientProperties.setAppname(appname);
            log.info(">>>>>>>>>>> xxl-job config appname 为空采用默认值: {}", appname);
        }
        String logPath = microFastXxlJobClientProperties.getLogPath();
        if (StringUtils.isBlank(logPath)) {
            logPath = System.getProperty("user.dir") + "/logs/" + environment.getProperty("spring.application.name") + "/xxljob/jobhandler";
            microFastXxlJobClientProperties.setLogPath(logPath);
            log.info(">>>>>>>>>>> xxl-job config logPath 为空采用默认值: {}", logPath);
        }
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(microFastXxlJobClientProperties.getAdminAddresses());
        xxlJobSpringExecutor.setAppname(microFastXxlJobClientProperties.getAppname());
        xxlJobSpringExecutor.setAddress(microFastXxlJobClientProperties.getAddress());
        xxlJobSpringExecutor.setIp(microFastXxlJobClientProperties.getIp());
        xxlJobSpringExecutor.setPort(microFastXxlJobClientProperties.getPort());
        xxlJobSpringExecutor.setAccessToken(microFastXxlJobClientProperties.getAccessToken());
        xxlJobSpringExecutor.setLogPath(microFastXxlJobClientProperties.getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(microFastXxlJobClientProperties.getLogRetentionDays());

        return xxlJobSpringExecutor;
    }

}
