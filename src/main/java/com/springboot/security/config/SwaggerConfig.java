package com.springboot.security.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi dividendApi() {
        return GroupedOpenApi.builder()
                .group("advanced-jpa")
                .pathsToMatch("/product/**", "/sign-api/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("스프링부트 SPRING SECURITY 학습 프로젝트")
                        .description("스프링부트 핵심가이드를 통해 스프링 SPRING SECURITY를 공부하는 프로젝트입니다.")
                        .version("1.0"));
    }
}
