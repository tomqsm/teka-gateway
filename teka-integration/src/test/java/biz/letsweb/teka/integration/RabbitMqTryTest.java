package biz.letsweb.teka.integration;

import com.rabbitmq.client.AMQP.Exchange;
import com.rabbitmq.client.impl.AMQImpl;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.runner.RunWith;
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
public class RabbitMqTryTest {

    @EndpointInject(uri = "direct:testQueue")
    protected ProducerTemplate testQueue;

    public RabbitMqTryTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of configure method, of class RabbitMqTry.
     */
    @Test
    @Ignore
    public void testConfigure() throws Exception {
        AMQImpl.Exchange exchange = new AMQImpl.Exchange();
        testQueue.sendBody(exchange);
        
        
    }

}
