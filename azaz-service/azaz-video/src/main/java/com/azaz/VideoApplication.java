package com.azaz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author c'y'x
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableDiscoveryClient
public class VideoApplication {
    public static void main(String[]args){
        SpringApplication.run(VideoApplication.class);
    }
}


