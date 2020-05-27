package com.micro.fast.log.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户基本信息
 *
 * @author shoufeng
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserInfoDto {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String username;
}
