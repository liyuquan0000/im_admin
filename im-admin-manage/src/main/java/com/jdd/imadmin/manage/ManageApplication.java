package com.jdd.imadmin.manage;

import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@MapperScan("com.jdd.imadmin.dao.mapper")
@EnableSwagger2
@SpringBootApplication(scanBasePackages="com.jdd.imadmin.*")
public class ManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageApplication.class, args);
    }
}
