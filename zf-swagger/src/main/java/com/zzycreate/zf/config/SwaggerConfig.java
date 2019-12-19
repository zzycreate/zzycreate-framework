package com.zzycreate.zf.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author zzycreate
 * @date 2019/12/19
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Value("${zf.swagger.title:接口文档}")
    private String title;
    @Value("${zf.swagger.description:接口文档说明}")
    private String description;
    @Value("${zf.swagger.version:1.0.0}")
    private String version;

    public SwaggerConfig() {
    }

    @Bean
    public Docket createRestApi() {
        return (new Docket(DocumentationType.SWAGGER_2)).apiInfo(this.apiInfo()).select().apis(RequestHandlerSelectors.basePackage("com")).paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return (new ApiInfoBuilder()).title(this.title).description(this.description).version(this.version).build();
    }

}
