package com.micro.fast.mybatisplus.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.micro.fast.mybatisplus.constant.OperationEnum;

import java.util.Arrays;
import java.util.List;

/**
 * service基础类
 *
 * @author shoufeng
 */
public interface BaseService<T> extends IService<T> {

    /**
     * 查询所有操作符
     *
     * @return
     */
    default List<OperationEnum> listOperationEnum() {
        return Arrays.asList(OperationEnum.values());
    }

    /**
     * 通过查询公共类查询列表
     *
     * @param baseQueryDto
     * @return
     */
    List<T> listByBaseQueryDto(BaseQueryDto<T> baseQueryDto);

    /**
     * 通过查询公共类查询列表（分页）
     *
     * @param baseQueryDto 查询公共类
     * @return 列表
     */
    Page<T> pageByBaseQueryDto(BaseQueryDto<T> baseQueryDto);
}
