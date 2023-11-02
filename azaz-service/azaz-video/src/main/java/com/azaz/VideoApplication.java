package com.azaz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author c'y'x
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableDiscoveryClient
@EnableAsync
@EnableFeignClients
public class VideoApplication {
    public static void main(String[]args){
        SpringApplication.run(VideoApplication.class);
    }
}


