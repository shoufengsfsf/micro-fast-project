package com.micro.fast.utils.ip;

import com.micro.fast.utils.ip.constant.IpEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * ip获取工具
 *
 * @author shoufeng
 */
@Slf4j
public class IpUtils {

    /**
     * 获取IP地址
     * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = null;
        try {
            ip = request.getHeader(IpEnum.X_FORWARDED_FOR.getValue());
            if (StringUtils.isEmpty(ip) || IpEnum.UNKNOWN.getValue().equalsIgnoreCase(ip)) {
                ip = request.getHeader(IpEnum.PROXY_CLIENT_IP.getValue());
            }
            if (StringUtils.isEmpty(ip) || ip.length() == 0 || IpEnum.UNKNOWN.getValue().equalsIgnoreCase(ip)) {
                ip = request.getHeader(IpEnum.WL_PROXY_CLIENT_IP.getValue());
            }
            if (StringUtils.isEmpty(ip) || IpEnum.UNKNOWN.getValue().equalsIgnoreCase(ip)) {
                ip = request.getHeader(IpEnum.HTTP_CLIENT_IP.getValue());
            }
            if (StringUtils.isEmpty(ip) || IpEnum.UNKNOWN.getValue().equalsIgnoreCase(ip)) {
                ip = request.getHeader(IpEnum.HTTP_X_FORWARDED_FOR.getValue());
            }
            if (StringUtils.isEmpty(ip) || IpEnum.UNKNOWN.getValue().equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
            log.error("获取ip失败: ", e);
        }

        return ip;
    }
}
