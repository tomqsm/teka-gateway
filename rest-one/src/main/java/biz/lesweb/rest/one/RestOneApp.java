package biz.lesweb.rest.one;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tomasz
 */
@SpringBootApplication
public class RestOneApp {

    static final Logger LOG = LoggerFactory.getLogger(RestOneApp.class);

    public static void main(String[] args) {
        LOG.info("Initialising ...");
        ApplicationContext ctx = SpringApplication.run(RestOneApp.class, args);
    }
}
