package biz.lesweb.rest.one.mvc;

import biz.lesweb.rest.one.RestOneApp;
import java.net.URL;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

/**
 * https://spring.io/guides/gs/spring-boot/
 * full-stack integration test
 * @author Tomasz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RestOneApp.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class RestOneControllerIT {

    @Value("${local.server.port}")
    private int port;

	private URL base;
	private RestTemplate template;

	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/");
		template = new TestRestTemplate();
	}

	@Test
	public void getHello() throws Exception {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		assertThat(response.getBody(), equalTo("Greetings from Spring Boot!"));
	}

}
