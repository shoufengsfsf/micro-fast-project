package com.micro.fast.swagger.configuration.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * swagger配置
 *
 * @author shoufeng
 */

@Data
@Component
@ConfigurationProperties(prefix = "micro.fast.swagger")
@NoArgsConstructor
@AllArgsConstructor
public class MicroFastSwaggerProperties {

    /**
     * 是否开启
     */
    private boolean enable = false;

    private List<MicroFastSwaggerDocket> microFastSwaggerDocketList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MicroFastSwaggerContact {

        /**
         * 联系人姓名
         */
        private String name;

        /**
         * 联系人url
         */
        private String url;

        /**
         * 联系人邮件
         */
        private String email;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MicroFastSwaggerApiInfo {

        /**
         * 版本
         */
        private String version = "";

        /**
         * 标题
         */
        private String title = "";

        /**
         * 描述
         */
        private String description = "";

        /**
         * 服务条款
         */
        private String termsOfServiceUrl = "";

        /**
         * 证书
         */
        private String license = "";

        /**
         * 证书地址
         */
        private String licenseUrl = "";

        /**
         * 联系人
         */
        private MicroFastSwaggerContact contact;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MicroFastSwaggerDocket {

        /**
         * 是否开启
         */
        private boolean enable = false;

        /**
         * host信息
         */
        private String host = "";

        /**
         * 分组名称
         */
        private String groupName = "";

        /**
         * swagger会解析的包路径
         **/
        private String basePackage = "";

        /**
         * swagger的api信息
         */
        private MicroFastSwaggerApiInfo microFastSwaggerApiInfo;
    }

}
