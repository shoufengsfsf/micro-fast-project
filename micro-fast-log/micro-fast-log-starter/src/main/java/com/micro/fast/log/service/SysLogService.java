package com.micro.fast.log.service;


import com.micro.fast.log.dto.SysLogDto;
import com.micro.fast.log.dto.SysUserInfoDto;
import com.micro.fast.utils.pojo.Result;

/**
 * 日志服务
 *
 * @author shoufeng
 */
public interface SysLogService {

    /**
     * 获取用户信息
     */
    Result<SysUserInfoDto> getUserInfo();

    /**
     * 日志入库
     */
    Boolean saveSysLog(SysLogDto sysLogDto);
}
