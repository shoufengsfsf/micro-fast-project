package com.micro.fast.auth.server;


import com.micro.fast.auth.jwt.server.EnableAuthServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author shoufeng
 */
@SpringBootApplication
@EnableAuthServer
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
