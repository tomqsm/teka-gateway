package biz.lesweb.rest.two;

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
public class RestTwoApp {

    static final Logger LOG = LoggerFactory.getLogger(RestTwoApp.class);

    public static void main(String[] args) {
        LOG.info("Initialising ...");
        ApplicationContext ctx = SpringApplication.run(RestTwoApp.class, args);
    }
}
