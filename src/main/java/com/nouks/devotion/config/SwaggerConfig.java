package com.nouks.devotion.config;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.HashSet;

/**
 * author nouks
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${spring.application.name}")
    private String appName;
    @Value("${spring.application.version}")
    private String appVersion;
    @Value("${swagger.base-package}")
    private String basePackage;
    @Bean
    public Docket api() {
        HashSet<String> consumesAndProduces =
                new HashSet<>(Collections.singletonList("application/json"));

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(metadata())
                .consumes(consumesAndProduces)
                .produces(consumesAndProduces)
                .pathMapping("/")
                .globalOperationParameters(
                        Collections.singletonList(new ParameterBuilder()
                                .name("Authorization")
                                .defaultValue("Bearer ")
                                .description("Security token")
                                .modelRef(new ModelRef("string"))
                                .parameterType("header")
                                .required(true)
                                .build()))
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .paths(Predicates.not(PathSelectors.regex("/actuator.*")))
                .build();
    }

    private ApiInfo metadata() {
        return new ApiInfoBuilder()
                .title(appName + " REST API")
                .description(appName + ": A Template Resource Server for Mungwin")
                .version(appVersion)
                .contact(new Contact("Mungwin Core", "https://mungwincore.mungwin/about/", "info@mungwincore.com"))
                .build();
    }
}
