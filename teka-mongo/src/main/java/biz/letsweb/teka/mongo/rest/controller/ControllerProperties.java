package biz.letsweb.teka.mongo.rest.controller;

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
@ConfigurationProperties(prefix = "content.repository.controller")
public class ControllerProperties {
    private final Map<String, String> hardware = new HashMap<>();

    public Map<String, String> getHardware() {
        return hardware;
    }

   
}
