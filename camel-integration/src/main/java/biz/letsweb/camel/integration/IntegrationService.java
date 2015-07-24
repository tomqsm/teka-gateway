package biz.letsweb.camel.integration;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 *
 * @author Tomasz
 */
@Component
public class IntegrationService extends RouteBuilder {

    @Override
    public void configure() throws Exception {
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
        from("jetty://http://localhost:8083?matchOnUriPrefix=true&throwExceptionOnFailure=false")
                .loadBalance()
//                .random()
                .roundRobin()
                .to("http://localhost:8080?throwExceptionOnFailure=true&bridgeEndpoint=true", 
                        "http://localhost:8090?throwExceptionOnFailure=true&bridgeEndpoint=true",
                        "http://localhost:8070?throwExceptionOnFailure=true&bridgeEndpoint=true"
                );
    }

}
