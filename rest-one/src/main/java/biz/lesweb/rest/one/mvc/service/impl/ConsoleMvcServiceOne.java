package biz.lesweb.rest.one.mvc.service.impl;

import biz.lesweb.rest.one.mvc.service.api.ServiceMvcOne;
import org.springframework.stereotype.Component;

/**
 *
 * @author tomasz
 */
@Component
public class ConsoleMvcServiceOne implements ServiceMvcOne {

    @Override
    public void doOne() {
        System.out.println("doing console service one MVC");
    }

}
