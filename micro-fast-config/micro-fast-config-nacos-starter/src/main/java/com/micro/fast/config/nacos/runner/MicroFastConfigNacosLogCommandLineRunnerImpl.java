package com.micro.fast.config.nacos.runner;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.micro.fast.config.nacos.constant.NacosConfigPropertiesEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Optional;

/**
 * @author shoufeng
 */
@Slf4j
@AllArgsConstructor
public class MicroFastConfigNacosLogCommandLineRunnerImpl implements CommandLineRunner {

    private NacosConfigProperties nacosConfigProperties;

    @Override
    public void run(String... args) throws Exception {
        Class<? extends NacosConfigProperties> nacosConfigPropertiesClass = nacosConfigProperties.getClass();
        Field[] declaredFields = nacosConfigPropertiesClass.getDeclaredFields();
        JSONArray logJsonArray = new JSONArray();
        for (Field declaredField : declaredFields) {
            //忽略private static final字段
            if (Modifier.isStatic(declaredField.getModifiers())) {
                continue;
            }
            declaredField.setAccessible(true);
            String declaredFieldName = declaredField.getName();
            Object declaredFieldValue = declaredField.get(nacosConfigProperties);
            Optional.ofNullable(NacosConfigPropertiesEnum.NACOS_CONFIG_PROPERTIES_ENUM_MAP.get(declaredFieldName))
                    .ifPresent(nacosDiscoveryPropertiesEnum -> {
                        JSONObject logJsonObject = new JSONObject();
                        logJsonObject.put("字段值: ", declaredFieldValue);
                        logJsonObject.put("字段名: ", declaredFieldName);
                        logJsonObject.put("描述: ", nacosDiscoveryPropertiesEnum.getDescription());
                        logJsonArray.add(logJsonObject);
                    });
        }
        if (logJsonArray.isEmpty()) {
            return;
        }
        log.info("NacosConfig配置: {}", logJsonArray.toJSONString());
    }
}
