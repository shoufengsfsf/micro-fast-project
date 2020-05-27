package com.micro.fast.mybatisplus.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 查询公共类
 *
 * @author shoufeng
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseQueryDto<T> {

    /**
     * 表实体
     */
    private Class<T> clazz;

    /**
     * 能否为空（true：可以，为空字段也查询，false：否，为空字段忽略不查询）
     */
    private Boolean isNullAble = false;

    /**
     * 字段操作映射，比如，name:like
     */
    private Map<String, String> fieldOperationMap;

    /**
     * 字段值映射，比如，name:张三
     */
    private Map<String, Object> fieldValueMap;

    public Map<String, String> getFieldOperationMap() {
        fieldOperationMap = Optional.ofNullable(fieldOperationMap).orElseGet(HashMap::new);
        return fieldOperationMap;
    }

    public Map<String, Object> getFieldValueMap() {
        fieldValueMap = Optional.ofNullable(fieldValueMap).orElseGet(HashMap::new);
        return fieldValueMap;
    }

    /**
     * 分页信息
     */
    private Page<T> page;

    public void setFieldValueMapByEntity(T entity) throws IllegalAccessException {
        this.clazz = (Class<T>) entity.getClass();
        Field[] fields = this.clazz.getDeclaredFields();
        if (ObjectUtils.isEmpty(fieldValueMap)) {
            fieldValueMap = new HashMap<>();
        }
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object fieldValue = field.get(entity);
            if (ObjectUtils.isEmpty(fieldValue) && !isNullAble) {
                continue;
            }
            fieldValueMap.put(fieldName, fieldValue);
        }
    }
}
