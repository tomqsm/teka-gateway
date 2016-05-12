package biz.letsweb.teka.mongo.configuration;

import com.google.common.base.Predicate;

import static com.google.common.base.Predicates.or;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static springfox.documentation.builders.PathSelectors.regex;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author toks
 */
@Configuration
@EnableSwagger2
public class SwaggerConfigurer {

    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select().paths(paths()).build();

    }


    @SuppressWarnings("unchecked")
    private Predicate<String> paths() {
        return or(
                regex("/hardware"),
                regex("/hardware/.*"),
                regex("/api/v1/.*")
        );
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "rest-repository",
                "REST backend for rest repository.",
                "1",
                "Terms",
                "letsweb.biz",
                "Licence",
                "#"
        );
    }
}
