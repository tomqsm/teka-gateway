package biz.lesweb.rest.one.rest;

import biz.lesweb.rest.one.rest.service.api.ServiceRestOne;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @RequestMapping(value="/getjson", produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonNode getJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String message = "{\"message\":\"hello world\"}";
        return mapper.readTree(message);
    }
}
