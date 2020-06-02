package com.micro.fast.office.configuration.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 配置值
 *
 * @author shoufeng
 */
@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "micro.fast.office")
public class MicroFastOfficeProperties {

    private boolean enable = false;

    /**
     * 基础路径
     */
    private String templateBasePath = System.getProperty("user.dir") + "/template/";

    /**
     * 基础pdf路径
     */
    private String pdfTemplateBasePath = "./pdf/";

    /**
     * 基础word路径
     */
    private String wordTemplateBasePath = "./word/";

    /**
     * 基础excel路径
     */
    private String excelTemplateBasePath = "./excel/";

}
