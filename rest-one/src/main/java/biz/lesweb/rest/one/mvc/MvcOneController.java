package biz.lesweb.rest.one.mvc;

import biz.lesweb.rest.one.mvc.service.api.ServiceMvcOne;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Tomasz
 */
@Controller
public class MvcOneController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MvcOneController.class);

    @Autowired
    private ServiceMvcOne serviceMvcOne;

    @RequestMapping("/greeting")
    public ModelAndView index(@RequestParam(value = "name", required = false, defaultValue = "World") String name, final ModelAndView modelAndView) {
        LOGGER.info("testing logger: {}", serviceMvcOne);
        serviceMvcOne.doOne();
        modelAndView.setViewName("greeting");
        modelAndView.addObject("name", String.format("żąć %s", name));
        return modelAndView;
    }
}
