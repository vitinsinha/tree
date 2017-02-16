package com.ncr;

import com.google.common.base.Predicates;
import com.ncr.rest.HelloController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableSwagger2
public class Application {


    private static HelloController helloController;

	public static void main(String[] args) {

	    SpringApplication.run(Application.class, args);

        //This is for creating the root node only once, just after the application startup
        helloController.createRootNode();
	}

    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
                .build();
    }

    @Autowired
    public void setController(HelloController controller) {
        helloController = controller;
    }

}
