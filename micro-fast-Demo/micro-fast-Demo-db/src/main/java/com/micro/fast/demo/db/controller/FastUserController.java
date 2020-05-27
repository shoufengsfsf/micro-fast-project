package com.micro.fast.demo.db.controller;

import com.alibaba.fastjson.JSON;
import com.micro.fast.demo.db.entity.FastUserEntity;
import com.micro.fast.demo.db.service.FastUserService;
import com.micro.fast.mybatisplus.base.BaseQueryDto;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author shoufeng
 */
@RestController
@RequestMapping("/fastuser")
public class FastUserController {

    @Resource
    private FastUserService fastUserService;

    @PostMapping("/save")
    public void save(@RequestParam String userName) {
        fastUserService.save(FastUserEntity.builder().userName(userName).build());
    }

    @PutMapping("/update")
    public void save(@RequestBody FastUserEntity fastUserEntity) {
        fastUserService.updateById(fastUserEntity);
    }

    @GetMapping("/list")
    public String list(@RequestBody BaseQueryDto<FastUserEntity> fastUserEntityBaseQueryDto) {
//        fastUserEntityBaseQueryDto.setClazz(FastUserEntity.class);
        return JSON.toJSONString(fastUserService.listByBaseQueryDto(fastUserEntityBaseQueryDto));
    }

    public static void main(String[] args) {
        BaseQueryDto<FastUserEntity> fastUserEntityBaseQueryDto = new BaseQueryDto<>();
        fastUserEntityBaseQueryDto.setClazz(FastUserEntity.class);
        fastUserEntityBaseQueryDto.getFieldValueMap().put("userName", "k");
        fastUserEntityBaseQueryDto.getFieldOperationMap().put("userName", "like");
        System.out.println(JSON.toJSONString(fastUserEntityBaseQueryDto));
    }
}
