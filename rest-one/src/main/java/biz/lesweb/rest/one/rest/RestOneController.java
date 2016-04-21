package biz.lesweb.rest.one.rest;

import biz.lesweb.rest.one.rest.service.api.ServiceRestOne;
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
    private ServiceRestOne serviceRestOne;
    
    @RequestMapping("/")
    public String index() { 
        serviceRestOne.doOne();
        return "Greetings from Rest 1!";
    }
}
