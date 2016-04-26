/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.letsweb.camel.integration;

import java.util.List;
import java.util.Map;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedCaseInsensitiveMap;

/**
 *
 * @author tomasz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IntegratorApp.class)
@WebIntegrationTest(randomPort = true)
public class DataSourceServiceTest {

    @Autowired
    CamelContext camelContext;

    @EndpointInject(uri = "mock:sql")
    protected MockEndpoint mockResult;

    @EndpointInject(uri = "{{sqltest}}")
    protected ProducerTemplate sqlTestProducerTemplate;

    @Test
    public void testEndpoint() throws InterruptedException {
        mockResult.expectedMessageCount(1);
        sqlTestProducerTemplate.sendBody(null);
        mockResult.assertIsSatisfied();
        final Map body =  (LinkedCaseInsensitiveMap) mockResult.getReceivedExchanges().get(0).getIn().getBody();
        Assert.assertEquals("żąć", body.get("value"));
    }
}
