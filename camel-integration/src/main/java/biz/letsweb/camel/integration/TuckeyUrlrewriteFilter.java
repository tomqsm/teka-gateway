package biz.letsweb.camel.integration;

import org.apache.camel.component.urlrewrite.HttpUrlRewrite;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author tomasz
 */
@Configuration
public class TuckeyUrlrewriteFilter {

    @Bean
    public HttpUrlRewrite urlRewriteFilter() {
        HttpUrlRewrite httpUrlRewrite = new HttpUrlRewrite();
        httpUrlRewrite.setConfigFile("urlrewrite.xml");
        return httpUrlRewrite;
    }


}
 