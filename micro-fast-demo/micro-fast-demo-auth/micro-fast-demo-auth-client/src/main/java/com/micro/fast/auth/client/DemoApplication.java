package com.micro.fast.auth.client;


import com.micro.fast.auth.jwt.client.EnableAuthClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author shoufeng
 */
@SpringBootApplication
@EnableAuthClient
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
