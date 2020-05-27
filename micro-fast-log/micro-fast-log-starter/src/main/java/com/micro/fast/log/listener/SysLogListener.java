package com.micro.fast.log.listener;


import com.micro.fast.log.dto.SysLogDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import java.util.function.Consumer;


/**
 * 异步监听日志事件
 *
 * @author shoufeng
 */
@Slf4j
@AllArgsConstructor
public class SysLogListener {

    private Consumer<SysLogDto> consumer;

    @Async
    @Order
    @EventListener(SysLogEvent.class)
    public void saveSysLog(SysLogEvent event) {
        SysLogDto sysLog = (SysLogDto) event.getSource();

        consumer.accept(sysLog);
    }

}
