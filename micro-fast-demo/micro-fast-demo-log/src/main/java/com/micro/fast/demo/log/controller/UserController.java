package com.micro.fast.demo.log.controller;

import com.micro.fast.demo.log.service.UserService;
import com.micro.fast.log.annotation.SysLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 模拟请求，所以我都用get了，方便一点
 *
 * @author shoufeng
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @SysLog(operation = "查询用户",enableDb = false)
    @GetMapping("/list")
    public List<String> list() {
        return userService.list();
    }

    @SysLog(operation = "新增用户",enableDb = true)
    @GetMapping("/save")
    public void save() {
        userService.save("赵六");
    }

    @SysLog(operation = "删除用户",enableDb = true)
    @GetMapping("/delete")
    public void delete() {
        userService.delete();
    }
}
