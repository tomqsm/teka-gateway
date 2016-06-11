package biz.letsweb.teka.integration;

import java.net.ConnectException;
import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Tomasz
 */
@Component
public class LoadblalnceService extends RouteBuilder {
    
    @Autowired
    LoadbalanceProps balancerProps;
    
    @Override
    public void configure() throws Exception {
        final Map<String, String> props = balancerProps.getLoadbalancer();
        from("{{jettyProxyEndpoint1}}")
                .onException(ConnectException.class)
                .handled(true)
                //                // create a custom failure response
                .transform(constant("Sorry, can't connect to the service."))
                //                // we must remember to set error code 500 as handled(true)
                //                // otherwise would let Camel thing its a OK response (200)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
                .end()
                .loadBalance()
                //                .random()
                //                .failover()
                .roundRobin()
                .to("{{app1}}");

        from("{{jettyProxyEndpoint2}}")
                .onException(ConnectException.class)
                .handled(true)
                .transform(constant("Sorry, can't connect to the service."))
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
                .end()
                .loadBalance()
                .roundRobin()
                .to("{{app1}}");
    }
    
}
