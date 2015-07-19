package biz.lesweb.rest.one;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author Tomasz
 */
@SpringBootApplication
public class RestOneApp {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(RestOneApp.class, args);
    }
}
