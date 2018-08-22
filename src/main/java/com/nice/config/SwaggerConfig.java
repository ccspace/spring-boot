package com.nice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @ProjectName: nice-springboot
 * @Package: com.nice.config
 * @ClassName: Swagger2Configuration
 * @Description: java类作用描述
 * @Author: baofei.
 * @CreateDate: 2018/8/16 13:51
 * @UpdateUser:
 * @UpdateDate: 2018/8/16 13:51
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * Copyright: Copyright (c) 2018
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nice.controller"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("springboot利用swagger构建api文档")
                .description("详细描述")
                .version("v1.0")
                .termsOfServiceUrl("terms of service")
                .contact("baofei")
                .license("The Apache License, Version 2.")
                .licenseUrl("ttp://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
    }
}
