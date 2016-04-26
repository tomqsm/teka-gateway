package biz.letsweb.camel.integration;

import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Tomasz
 */
@Component
public class IntegrationService extends RouteBuilder {

    @Autowired
    LoadBalancerProps balancerProps;

    @Override
    public void configure() throws Exception {
        final Map<String, String> props = balancerProps.getLoadbalancer();
        System.out.println(String.format("XX yeah %s", props));
//        from("file:data/inbox?noop=true")
//                .choice().when(header("CamelFileName").endsWith(".xml"))
//                .process(new Processor() {
//                    @Override
//                    public void process(Exchange exchange) throws Exception {
//                        Message message = exchange.getIn();
//                        String body = message.getBody(String.class);
//                        body = body + " enriched " + message.getHeader("CamelFileName", String.class);
//                        message.setBody(body, String.class);
//                        exchange.setIn(message);
//                    }
//                }).convertBodyTo(String.class)
//                .to("file:data/outbox");
        from(String.format("%s:%s?%s&%s", 
                props.get("component"),
                props.get("host"),
                props.get("matchOnUriPrefix"),
                props.get("throwExceptionOnFailure")))
                .process((Exchange exchange) -> {
                    Message message = exchange.getIn();
                    System.out.println(message.getHeaders());
                })
                .loadBalance()
                //                .random()
                //                                .failover(2, true, true, ConnectException.class)
                .roundRobin()
                .to(
                        //                        "http://localhost:8080?throwExceptionOnFailure=true&bridgeEndpoint=true",
                        "http://localhost:8070/rest-one?throwExceptionOnFailure=true&bridgeEndpoint=true"
                //                        "http://localhost:8071?throwExceptionOnFailure=true&bridgeEndpoint=true"
                );
    }

}
