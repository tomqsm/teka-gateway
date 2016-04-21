package biz.lesweb.rest.one.mvc.service.impl;

import biz.lesweb.rest.one.mvc.service.api.ServiceOne;
import org.springframework.stereotype.Component;

/**
 *
 * @author tomasz
 */
@Component
public class ConsoleServiceOne implements ServiceOne {

    @Override
    public void doOne() {
        System.out.println("doing console service one");
    }

}
