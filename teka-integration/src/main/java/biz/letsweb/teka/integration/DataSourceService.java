package biz.letsweb.teka.integration;

import java.util.List;
import javax.sql.DataSource;
import org.apache.camel.Exchange;
import org.apache.camel.api.management.ManagedResource;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedCaseInsensitiveMap;

/**
 *
 * @author tomasz
 */
@Component
@ManagedResource
public class DataSourceService extends RouteBuilder {

    @Autowired
    private DataSource dataSource;

    @Override
    public void configure() throws Exception {
        from("{{findAllProps}}").to("{{sqltest}}")
                .process((Exchange exchange) -> {
                    final List<LinkedCaseInsensitiveMap> body = (List<LinkedCaseInsensitiveMap>) exchange.getIn().getBody();
                    final LinkedCaseInsensitiveMap firstRecord = body.stream().findFirst().orElse(new LinkedCaseInsensitiveMap());
                    System.out.println(firstRecord);
                })
                .log("${threadName}")
                .to("mock:sql");
    }

}
