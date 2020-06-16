package com.jdd.imadmin;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author @sailength on 2020/3/3.
 */
@MapperScan("com.jdd.imadmin.dao.mapper")
@SpringBootApplication(scanBasePackages="com.jdd.imadmin.*")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
