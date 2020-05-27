package com.micro.fast.mybatisplus.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * serviceImpl基础类
 *
 * @author shoufeng
 */
public class BaseServiceImpl<M extends BaseDao<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    @Override
    public List<T> listByBaseQueryDto(BaseQueryDto<T> baseQueryDto) {

        return baseMapper.selectListByBaseQueryDto(baseQueryDto);
    }

    @Override
    public Page<T> pageByBaseQueryDto(BaseQueryDto<T> baseQueryDto) {

        return baseMapper.selectPageByBaseQueryDto(baseQueryDto);
    }

}
