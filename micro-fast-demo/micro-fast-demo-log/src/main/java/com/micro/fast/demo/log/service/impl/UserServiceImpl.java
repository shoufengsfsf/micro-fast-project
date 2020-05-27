package com.micro.fast.demo.log.service.impl;

import com.micro.fast.demo.log.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Override
    public void save(String userName) {
        log.info("新增用户: {}", userName);
    }

    @Override
    public List<String> list() {
        List<String> list = Stream.of("张三", "里斯", "王武").collect(Collectors.toList());
        log.info("查询用户: {}", list);
        return list;
    }

    @Override
    public void delete() {
        log.warn("删除用户: {}", "张三");
    }
}
