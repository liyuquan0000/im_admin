package com.jdd.imadmin.manage.swagger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Configuration
@Profile("dev")
@Import(SwaggerConfig.class)
public class SwaggerAutoConfiguration {

    @Bean
    public Docket createRestApi(SwaggerConfig swaggerConfig) {
        String env = swaggerConfig.getEnv();
        String basePackage = "com.jdd.imadmin.manage.controller";
        boolean isShowSwagger = StringUtils.isNotEmpty(env) && (Objects.equals(env, "dev") || Objects.equals(env, "test"));
        //添加header参数
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        ticketPar.name("token").description("user token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build(); //header中的token参数非必填，传空也可以
        pars.add(ticketPar.build());

        return (new Docket(DocumentationType.SWAGGER_2))
                .enable(true)
                .apiInfo(this.apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(isShowSwagger ? PathSelectors.any() : PathSelectors.none())
                .build()
                .globalOperationParameters(pars);
    }

    private ApiInfo apiInfo() {
        String defaultTitle = "接口文档与测试地址";
        String defaultDescription = "接口文档与测试地址";
        String defaultVersion = "1.0.0";
        return (new ApiInfoBuilder())
                .title(defaultTitle)
                .description(defaultDescription)
                .version(defaultVersion)
                .build();
    }
}
