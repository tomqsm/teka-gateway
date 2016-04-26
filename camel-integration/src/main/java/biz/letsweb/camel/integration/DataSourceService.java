package biz.letsweb.camel.integration;

import javax.sql.DataSource;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author tomasz
 */
@Component
public class DataSourceService extends RouteBuilder{
    
    @Autowired
    private DataSource dataSource;

    @Override
    public void configure() throws Exception {
        from("{{sqltest}}")
                .log("response body: ${body}")
            .to("mock:sql");
    }

//    @Bean
//    ServletRegistrationBean h2servletRegistration() {
//        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
//        registrationBean.addUrlMappings("/console/*");
//        return registrationBean;
//    }
}
