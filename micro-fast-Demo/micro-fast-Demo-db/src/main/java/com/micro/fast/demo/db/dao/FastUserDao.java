package com.micro.fast.demo.db.dao;

import com.micro.fast.demo.db.entity.FastUserEntity;
import com.micro.fast.mybatisplus.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户dao
 *
 * @author shoufeng
 */
@Mapper
public interface FastUserDao extends BaseDao<FastUserEntity> {

}
