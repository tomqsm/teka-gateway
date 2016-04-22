package biz.letsweb.rest.two.mvc;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author Tomasz
 */
@RestController
public class RestTwoController {
    @RequestMapping("/")
    public String index() {
        return "Greetings from Rest 2!";
    }
}
