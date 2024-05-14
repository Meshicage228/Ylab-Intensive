package trainingDiary.com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("trainingDiary.com.in.controllers"))
                .build()
                .apiInfo(apiInfo());
    }

    protected ApiInfo apiInfo(){
        return new ApiInfo(
                "Training Diary Application",
                "Rest API documentation",
                "1",
                "Terms of Service",
                new Contact("Me", "", "meshicage201gmail.com"),
                "", "", Collections.emptyList()
        );
    }
}