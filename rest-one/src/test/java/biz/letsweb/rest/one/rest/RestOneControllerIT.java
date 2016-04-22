package biz.letsweb.rest.one.rest;
import biz.letsweb.rest.one.RestOneApp;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

/**
 * https://spring.io/guides/gs/spring-boot/ full-stack integration test
 *
 * @author Tomasz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RestOneApp.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class RestOneControllerIT {
    static final Logger LOG = LoggerFactory.getLogger(RestOneControllerIT.class);

    @Value("${local.server.port}")
    private int port;
    @Value("${server.contextPath}")
    private String serverContextPath;
    private URL base;
    private RestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + serverContextPath);
        template = new TestRestTemplate();
    }

    @Test
    public void controllerWithRealContextHasAutowiredField() throws Exception {
        LOG.info("started");
        ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
        assertThat(response.getBody(), equalTo("Greetings from Rest 1!"));
    }
    

    @Test
    public void controllerTestedWithRestTemplate() throws IOException{
        final URL url = new URL(base +"/getjson");
        ResponseEntity<String> response = template.getForEntity(url.toString(), String.class);
        final String body = response.getBody();
        final List<String> serverHeader = response.getHeaders().get("Server");
        
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(body);
        JsonNode name = root.path("message");
        
        assertThat(name.asText(), is("hello world"));
        assertThat(serverHeader.get(0), is("Apache-Coyote/1.1"));
    }

}
