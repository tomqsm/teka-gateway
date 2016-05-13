package biz.letsweb.teka.integration;

import biz.letsweb.teka.integration.IntegratorApp;
import java.util.concurrent.TimeUnit;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author tomasz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IntegratorApp.class)
@WebIntegrationTest(randomPort = true)
public class LoadblalnceServiceTest {

    @Autowired
    CamelContext camelContext;
    
    @EndpointInject(uri = "{{testEndpoint}}")
    protected MockEndpoint mockResult;

    @EndpointInject(uri = "http://localhost:8083")
    protected ProducerTemplate jettyLoadBalancerProducerTemplate;

    @Test
    @Ignore
    public void testEndpoint() throws InterruptedException {
        final String out = jettyLoadBalancerProducerTemplate.requestBody((Object) null, String.class);
        assertEquals("Greetings from Rest 1!", out);
    }

    @Test
    @Ignore (value = "this is not working, you didn't understand how to use notifybuilder")
    public void anotherwayOfStartTestingEndpoint() throws InterruptedException {
        NotifyBuilder notify = new NotifyBuilder(camelContext)
                .wereSentTo("http://localhost:8083")
                .whenDone(1)
                .create();
        assertTrue(notify.matches(10, TimeUnit.SECONDS));
    }

}
