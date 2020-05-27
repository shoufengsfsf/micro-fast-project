package com.micro.fast.demo.log.service;

import java.util.List;

/**
 * @author shoufeng
 */
public interface UserService {

    /**
     * 新增用户
     *
     * @param userName
     */
    void save(String userName);

    /**
     * 查询用户
     */
    List<String> list();

    /**
     * 删除用户
     */
    void delete();
}
