package com.micro.fast.log.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志基本信息
 *
 * @author shoufeng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysLogDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long logId;
    /**
     * sessionId
     */
    private String sessionId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户操作描述（比如：登录）
     */
    private String operation;
    /**
     * 请求方法（比如：SysUserController.login）
     */
    private String method;
    /**
     * 请求参数
     */
    private String reqParam;
    /**
     * 执行时长(毫秒)
     */
    private Long executeTime;
    /**
     * IP地址
     */
    private String ip;
    /**
     * 地址（通过ip拿到地址）
     */
    private String region;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

}
