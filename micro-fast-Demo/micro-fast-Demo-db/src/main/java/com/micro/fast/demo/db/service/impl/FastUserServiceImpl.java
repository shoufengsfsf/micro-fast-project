package com.micro.fast.demo.db.service.impl;

import com.micro.fast.demo.db.dao.FastUserDao;
import com.micro.fast.demo.db.entity.FastUserEntity;
import com.micro.fast.demo.db.service.FastUserService;
import com.micro.fast.mybatisplus.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author shoufeng
 */
@Service
public class FastUserServiceImpl extends BaseServiceImpl<FastUserDao, FastUserEntity> implements FastUserService {
}
