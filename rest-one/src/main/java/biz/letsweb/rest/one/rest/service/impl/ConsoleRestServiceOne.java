package biz.letsweb.rest.one.rest.service.impl;

import biz.letsweb.rest.one.rest.service.api.ServiceRestOne;
import org.springframework.stereotype.Component;

/**
 *
 * @author tomasz
 */
@Component
public class ConsoleRestServiceOne implements ServiceRestOne {

    @Override
    public void doOne() {
        System.out.println("doing console service one REST");
    }

}
