package biz.lesweb.rest.one.mvc.service.impl;

import biz.lesweb.rest.one.mvc.service.api.ServiceMvcOne;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author tomasz
 */
@Component
public class ConsoleMvcServiceOne implements ServiceMvcOne {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleMvcServiceOne.class);

    @Override
    public void doOne() {
        LOGGER.info("doing console service one MVC");
    }

}
