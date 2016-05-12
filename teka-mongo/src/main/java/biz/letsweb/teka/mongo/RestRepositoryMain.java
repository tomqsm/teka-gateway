package biz.letsweb.teka.mongo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author tomasz
 */
@SpringBootApplication
public class RestRepositoryMain {

    public static final Logger LOG = LoggerFactory.getLogger(RestRepositoryMain.class);

    public static void main(String[] args) {
        LOG.info("Initialising ...");
        SpringApplication.run(RestRepositoryMain.class, args);
    }

}
