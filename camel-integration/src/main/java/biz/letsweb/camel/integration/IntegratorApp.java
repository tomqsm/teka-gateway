package biz.letsweb.camel.integration;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.spring.boot.FatJarRouter;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author Tomasz
 */
@SpringBootApplication
public class IntegratorApp extends FatJarRouter {

    @Override
    public void configure() {
        from("file:data/inbox?noop=true")
                .choice().when(header("CamelFileName").endsWith(".xml"))
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        Message message = exchange.getIn();
                        String body = message.getBody(String.class);
                        body = body + " enriched " + message.getHeader("CamelFileName", String.class);
                        message.setBody(body, String.class);
                        exchange.setIn(message);
                    }
                }).convertBodyTo(String.class)
                .to("file:data/outbox");
    }

}
