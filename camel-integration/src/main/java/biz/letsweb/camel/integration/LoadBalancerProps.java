package biz.letsweb.camel.integration;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author tomasz
 */
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "camel.prod")
public class LoadBalancerProps {
    
    private final Map<String, String> loadbalancer = new HashMap<>();

    public Map<String, String> getLoadbalancer() {
        return loadbalancer;
    }

}
