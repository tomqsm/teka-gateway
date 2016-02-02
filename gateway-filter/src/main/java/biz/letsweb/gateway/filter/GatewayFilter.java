package biz.letsweb.gateway.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.DispatcherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

/**
 *
 * @author Tomasz
 */
@SpringBootApplication
public class GatewayFilter {

    static final Logger LOG = LoggerFactory.getLogger(GatewayFilter.class);

    public static void main(String[] args) {
        LOG.info("Initialising ...");
        ApplicationContext ctx = SpringApplication.run(GatewayFilter.class, args);
    }

    @Bean
    public FilterRegistrationBean urlRewriteFilter() {
        UrlRewriteFilter urlRewriteFilter = new UrlRewriteFilter();
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setName("UrlRewriteFilter");
        List<String> urlPatterns = new ArrayList<>(2);
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);
        registrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD);
        registrationBean.setAsyncSupported(true);
        Map<String, String> initParams = new HashMap<>(5);
        initParams.put("confReloadCheckInterval", "60");
        initParams.put("logLevel", "info");
        initParams.put("statusPath", "/status");
        registrationBean.setInitParameters(initParams);
        registrationBean.setFilter(urlRewriteFilter);
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
