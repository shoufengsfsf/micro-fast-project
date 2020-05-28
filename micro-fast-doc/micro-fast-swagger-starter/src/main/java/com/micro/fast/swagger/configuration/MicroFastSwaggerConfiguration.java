package com.micro.fast.swagger.configuration;

import com.micro.fast.swagger.configuration.properties.MicroFastSwaggerProperties;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * swagger配置
 *
 * @author shoufeng
 */
@Configuration
@ConditionalOnProperty(prefix = "micro.fast.swagger", value = "enable", havingValue = "true")
@ComponentScan(basePackages = {"com.micro.fast.swagger.configuration"})
@EnableSwagger2
public class MicroFastSwaggerConfiguration {

    @Resource
    private MicroFastSwaggerProperties microFastSwaggerProperties;

    @Resource
    private BeanFactory beanFactory;

    @Bean
    @ConditionalOnMissingBean
    public List<Docket> docketList() {
        ConfigurableBeanFactory configurableBeanFactory = (ConfigurableBeanFactory) beanFactory;
        List<Docket> docketList = createDocket(microFastSwaggerProperties);
        docketList.forEach(docket -> {
            configurableBeanFactory.registerSingleton(docket.getGroupName(), docket);
        });
        return docketList;
    }

    private List<Docket> createDocket(MicroFastSwaggerProperties microFastSwaggerProperties) {
        List<MicroFastSwaggerProperties.MicroFastSwaggerDocket> microFastSwaggerDocketList = microFastSwaggerProperties.getMicroFastSwaggerDocketList();
        return microFastSwaggerDocketList.stream().map(microFastSwaggerDocket -> {
            ApiInfo apiInfo = createApiInfo(microFastSwaggerDocket.getMicroFastSwaggerApiInfo());

            return new Docket(DocumentationType.SWAGGER_2)
                    .groupName(microFastSwaggerDocket.getGroupName())
                    .host(microFastSwaggerDocket.getHost())
                    .apiInfo(apiInfo).select()
                    .apis(RequestHandlerSelectors.basePackage(microFastSwaggerDocket.getBasePackage()))
                    .paths(PathSelectors.any())
                    .build();
        }).collect(Collectors.toList());
    }

    private ApiInfo createApiInfo(MicroFastSwaggerProperties.MicroFastSwaggerApiInfo microFastSwaggerApiInfo) {
        return new ApiInfoBuilder()
                .title(microFastSwaggerApiInfo.getTitle())
                .description(microFastSwaggerApiInfo.getDescription())
                .version(microFastSwaggerApiInfo.getVersion())
                .license(microFastSwaggerApiInfo.getLicense())
                .licenseUrl(microFastSwaggerApiInfo.getLicenseUrl())
                .contact(new Contact(microFastSwaggerApiInfo.getContact().getName(),
                        microFastSwaggerApiInfo.getContact().getUrl(),
                        microFastSwaggerApiInfo.getContact().getEmail()))
                .termsOfServiceUrl(microFastSwaggerApiInfo.getTermsOfServiceUrl())
                .build();
    }

}
