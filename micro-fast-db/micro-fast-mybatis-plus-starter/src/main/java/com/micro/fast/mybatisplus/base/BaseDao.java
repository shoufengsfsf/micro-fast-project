package com.micro.fast.mybatisplus.base;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import com.google.common.base.Strings;
import com.micro.fast.mybatisplus.constant.MicroFastMybatisPlusExceptionCodeEnum;
import com.micro.fast.mybatisplus.constant.OperationEnum;
import com.micro.fast.mybatisplus.exception.MicroFastMybatisPlusException;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

/**
 * dao层基础类
 *
 * @author shoufeng
 */
public interface BaseDao<T> extends BaseMapper<T> {

    /**
     * 通过查询公共类查询列表
     *
     * @param baseQueryDto 查询公共类
     * @return 列表
     */
    default List<T> selectListByBaseQueryDto(BaseQueryDto<T> baseQueryDto) {

        return selectList(getQueryWrapper(baseQueryDto));
    }


    /**
     * 通过查询公共类查询列表（分页）
     *
     * @param baseQueryDto 查询公共类
     * @return 列表
     */
    default Page<T> selectPageByBaseQueryDto(BaseQueryDto<T> baseQueryDto) {

        return selectPage(baseQueryDto.getPage(), getQueryWrapper(baseQueryDto));
    }

    /**
     * 获取QueryWrapper
     *
     * @param baseQueryDto
     * @return
     */
    default QueryWrapper<T> getQueryWrapper(BaseQueryDto<T> baseQueryDto) {
        Class<T> clazz = baseQueryDto.getClazz();
        Boolean isNullAble = baseQueryDto.getIsNullAble();
        Map<String, String> fieldOperationMap = baseQueryDto.getFieldOperationMap();
        Map<String, Object> fieldValueMap = baseQueryDto.getFieldValueMap();

        QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
        if (ObjectUtils.isEmpty(fieldValueMap) || fieldValueMap.isEmpty()) {
            return queryWrapper;
        }
        fieldValueMap.forEach((fieldName, fieldValue) -> {
            // 驼峰转下划线, userName -> user_name
            Converter<String, String> converter = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.LOWER_UNDERSCORE);
            String columnName = converter.convert(fieldName);
            if (Strings.isNullOrEmpty(columnName)) {
                return;
            }
            if (ObjectUtils.isEmpty(fieldOperationMap) || Strings.isNullOrEmpty(fieldOperationMap.get(fieldName))) {
                if (ObjectUtils.isEmpty(fieldValue)) {
                    if (isNullAble) {
                        queryWrapper.isNull(columnName);
                    }
                    return;
                }
                queryWrapper.eq(columnName, fieldValue);
                return;
            }
            String fieldOperation = fieldOperationMap.get(fieldName);
            OperationEnum operationEnum = OperationEnum.valueOf(fieldOperation.toUpperCase());
            switch (operationEnum) {
                case EQ: {
                    queryWrapper.eq(columnName, fieldValue);
                    break;
                }
                case NE: {
                    queryWrapper.ne(columnName, fieldValue);
                    break;
                }
                case GT: {
                    queryWrapper.gt(columnName, fieldValue);
                    break;
                }
                case GE: {
                    queryWrapper.ge(columnName, fieldValue);
                    break;
                }
                case LT: {
                    queryWrapper.lt(columnName, fieldValue);
                    break;
                }
                case LE: {
                    queryWrapper.le(columnName, fieldValue);
                    break;
                }
                case BETWEEN: {
                    List<String> betweenList = JSON.parseArray((String) fieldValue, String.class);
                    if (betweenList.size() < 2) {
                        throw new MicroFastMybatisPlusException(MicroFastMybatisPlusExceptionCodeEnum.FAIL.getCode(), "BETWEEN查询参数少于两个");
                    }
                    queryWrapper.between(columnName, betweenList.get(0), betweenList.get(1));
                    break;
                }
                case NOTBETWEEN: {
                    List<String> notBetweenList = JSON.parseArray((String) fieldValue, String.class);
                    if (notBetweenList.size() < 2) {
                        throw new MicroFastMybatisPlusException(MicroFastMybatisPlusExceptionCodeEnum.FAIL.getCode(), "NOTBETWEEN查询参数少于两个");
                    }
                    queryWrapper.notBetween(columnName, notBetweenList.get(0), notBetweenList.get(1));
                    break;
                }
                case LIKE: {
                    queryWrapper.like(columnName, fieldValue);
                    break;
                }
                case NOTLIKE: {
                    queryWrapper.notLike(columnName, fieldValue);
                    break;
                }
                case LIKELEFT: {
                    queryWrapper.likeLeft(columnName, fieldValue);
                    break;
                }
                case LIKERIGHT: {
                    queryWrapper.likeRight(columnName, fieldValue);
                    break;
                }
                case ISNULL: {
                    queryWrapper.isNull(columnName);
                    break;
                }
                case ISNOTNULL: {
                    queryWrapper.isNotNull(columnName);
                    break;
                }
                case IN: {
                    List<String> inList = JSON.parseArray((String) fieldValue, String.class);
                    queryWrapper.in(columnName, inList);
                    break;
                }
                case NOTIN: {
                    List<String> inList = JSON.parseArray((String) fieldValue, String.class);
                    queryWrapper.notIn(columnName, inList);
                    break;
                }
                case INSQL: {
                    queryWrapper.inSql(columnName, (String) fieldValue);
                    break;
                }
                case NOTINSQL: {
                    queryWrapper.notInSql(columnName, (String) fieldValue);
                    break;
                }
                case GROUPBY: {
                    queryWrapper.groupBy(columnName);
                    break;
                }
                case HAVING: {
                    List<String> havingList = JSON.parseArray((String) fieldValue, String.class);
                    queryWrapper.having(columnName, havingList);
                    break;
                }
                case ORDERBYASC: {
                    queryWrapper.orderByAsc(columnName);
                    break;
                }
                case ORDERBYDESC: {
                    queryWrapper.orderByDesc(columnName);
                    break;
                }
                default: {
                    throw new MicroFastMybatisPlusException(MicroFastMybatisPlusExceptionCodeEnum.FAIL.getCode(), "未找到具体操作符");
                }
            }
        });

        return queryWrapper;
    }
}
