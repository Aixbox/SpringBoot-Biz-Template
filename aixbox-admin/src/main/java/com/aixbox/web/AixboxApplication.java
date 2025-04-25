package com.aixbox.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动程序
 */
@SpringBootApplication(scanBasePackages = {"${aixbox.info.base-package}"})
public class AixboxApplication {

    public static void main(String[] args) {
        SpringApplication.run(AixboxApplication.class, args);
    }

}
