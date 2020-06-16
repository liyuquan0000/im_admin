package com.jdd.imadmin.api;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;


@MapperScan("com.jdd.imadmin.dao.mapper")
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = "com.jdd.imadmin.*")
public class ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
