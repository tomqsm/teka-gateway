package biz.letsweb.rest.one.mvc;

import biz.letsweb.rest.one.mvc.service.api.ServiceMvcOne;
import biz.letsweb.rest.one.mvc.validation.ValidName;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ModelAndView index(@ModelAttribute @Valid NameParam nameParam, final ModelAndView modelAndView) {
        LOGGER.info("testing logger: {}", serviceMvcOne);
        serviceMvcOne.doOne();
        modelAndView.setViewName("greeting");
        modelAndView.addObject("name", String.format("żąć %s", nameParam.getName()));
        return modelAndView;
    }
    
    static class NameParam{
        
        @ValidName(message = "allowed names are tomasz or nothing")
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
        
        
    }
}
