package com.micro.fast.utils.ip.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ip相关的一些常量
 *
 * @author shoufeng
 */

@AllArgsConstructor
public enum IpEnum {
    /**
     * ip相关的一些请求头
     */
    UNKNOWN("unknown"),
    X_FORWARDED_FOR("x-forwarded-for"),
    PROXY_CLIENT_IP("Proxy-Client-IP"),
    WL_PROXY_CLIENT_IP("WL-Proxy-Client-IP"),
    HTTP_CLIENT_IP("HTTP_CLIENT_IP"),
    HTTP_X_FORWARDED_FOR("HTTP_X_FORWARDED_FOR")
    ;
    @Getter
    private String value;
}
