package com.qiuyu.zhxy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.qiuyu.zhxy.mapper")
@EnableTransactionManagement
public class ZhxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhxyApplication.class, args);
    }

}
