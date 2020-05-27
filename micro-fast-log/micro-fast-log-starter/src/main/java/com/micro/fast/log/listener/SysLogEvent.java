package com.micro.fast.log.listener;

import com.micro.fast.log.dto.SysLogDto;
import org.springframework.context.ApplicationEvent;

/**
 * 系统日志事件
 *
 * @author shoufeng
 */
public class SysLogEvent extends ApplicationEvent {

    public SysLogEvent(SysLogDto source) {
        super(source);
    }
}
