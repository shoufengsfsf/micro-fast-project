package com.micro.fast.demo.log.service.impl;

import com.alibaba.fastjson.JSON;
import com.micro.fast.log.dto.SysLogDto;
import com.micro.fast.log.dto.SysUserInfoDto;
import com.micro.fast.log.service.SysLogService;
import com.micro.fast.utils.code.ResultCode;
import com.micro.fast.utils.pojo.Result;
import org.springframework.stereotype.Service;

@Service
public class SysLogServiceImpl implements SysLogService {

    /**
     * 模拟获取登录信息
     *
     * @return
     */
    @Override
    public Result<SysUserInfoDto> getUserInfo() {
        Result<SysUserInfoDto> sysUserInfoDtoResult = new Result<>();
        sysUserInfoDtoResult.setCode(ResultCode.SUCCESS.getCode());
        SysUserInfoDto sysUserInfoDto = new SysUserInfoDto();
        sysUserInfoDto.setUsername("张三");
        sysUserInfoDtoResult.setData(sysUserInfoDto);
        return sysUserInfoDtoResult;
    }

    /**
     * 模拟入库操作
     *
     * @param sysLogDto
     * @return
     */
    @Override
    public Boolean saveSysLog(SysLogDto sysLogDto) {
        System.out.println("日志入库模拟操作；" + JSON.toJSONString(sysLogDto));
        return true;
    }

}
