package com.jdd.imadmin.manage.swagger;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@Data
public class SwaggerConfig {

    @Value(value = "${spring.profiles.active}")
    private String env;

}