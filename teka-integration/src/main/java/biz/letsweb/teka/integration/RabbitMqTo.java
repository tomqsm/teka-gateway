package biz.letsweb.teka.integration;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 *
 * @author tomasz
 */
@Component
public class RabbitMqTo extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // "rabbitmq://localhost/general.topic?queue=general.queue&amp;routingKey=general.queue&amp;exchangeType=topic&amp;username=guest&amp;password=guest&amp;autoDelete=false&amp;durable=true"
        from("direct:testQueue")
                .to("rabbitmq://localhost:5672/task?queue=testQueue&routingKey=general.queue&exchangeType=direct&username=guest&password=guest&autoDelete=false&durable=true") //                .to("rabbitmq://localhost:5672/task?username=guest&password=guest&autoDelete=false&routingKey=camel&queue=testQueue")
                ;
    }

}
