package biz.letsweb.gateway.filter.mvc;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author Tomasz
 */
@RestController
public class RestOneController {
    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
