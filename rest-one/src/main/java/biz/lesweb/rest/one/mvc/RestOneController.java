package biz.lesweb.rest.one.mvc;

import biz.lesweb.rest.one.mvc.service.api.ServiceOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author Tomasz
 */
@RestController
public class RestOneController {
    
    @Autowired
    private ServiceOne serviceOne;
    
    @RequestMapping("/")
    public String index() { 
        serviceOne.doOne();
        return "Greetings from Rest 1!";
    }
}
