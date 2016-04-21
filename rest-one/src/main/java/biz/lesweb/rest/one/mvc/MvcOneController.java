package biz.lesweb.rest.one.mvc;

import biz.lesweb.rest.one.mvc.service.api.ServiceMvcOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Autowired
    private ServiceMvcOne serviceMvcOne;

    @RequestMapping("/greeting")
    public ModelAndView index(@RequestParam(value = "name", required = false, defaultValue = "World") String name, final ModelAndView modelAndView) {
        serviceMvcOne.doOne();
        modelAndView.setViewName("greeting");
        modelAndView.addObject("name", name);
        return modelAndView;
    }
}
