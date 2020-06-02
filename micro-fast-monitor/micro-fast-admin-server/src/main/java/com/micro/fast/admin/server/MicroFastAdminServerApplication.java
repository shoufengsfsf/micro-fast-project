package com.micro.fast.admin.server;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 应用启动入口
 *
 * @author shoufeng
 */
@EnableAdminServer
@SpringBootApplication
public class MicroFastAdminServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroFastAdminServerApplication.class, args);
    }

}
