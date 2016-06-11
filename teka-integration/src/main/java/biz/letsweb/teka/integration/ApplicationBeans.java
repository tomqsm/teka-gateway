package biz.letsweb.teka.integration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.FieldNamingPolicy;
import org.apache.camel.CamelContext;
import org.apache.camel.component.gson.GsonDataFormat;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.component.urlrewrite.HttpUrlRewrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author tomasz
 */
@Configuration
public class ApplicationBeans {
    
    @Autowired
    private CamelContext camelContext;

    @Bean
    public HttpUrlRewrite urlRewriteFilter() {
        HttpUrlRewrite httpUrlRewrite = new HttpUrlRewrite();
        httpUrlRewrite.setConfigFile("urlrewrite.xml");
        return httpUrlRewrite;
    }

    @Bean
    String myBean() {
        return "I'm Spring bean!";
    }

    @Bean
    Giraffe giraffe() {
        Giraffe giraffe = new Giraffe();
        giraffe.setName(null);
        giraffe.setNeckLength(123);
        return giraffe;
    }

    @Bean
    GsonDataFormat gsonDataFormat() {
        GsonDataFormat gsonDataFormat = new GsonDataFormat();
        gsonDataFormat.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonDataFormat;
    }

    @Bean
    JacksonDataFormat jacksonDataFormat() {
        JacksonDataFormat jacksonDataFormat = new JacksonDataFormat();
        jacksonDataFormat.setAllowJmsType(true);
        jacksonDataFormat.setInclude(JsonInclude.Include.NON_NULL.name());
        return jacksonDataFormat;
    }

}
