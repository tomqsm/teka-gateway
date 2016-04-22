package biz.letsweb.rest.one;

import java.io.File;
import java.io.IOException;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.coyote.http11.Http11Protocol;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.SocketUtils;
import ch.qos.logback.access.tomcat.LogbackValve;
import java.util.Arrays;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.http.HttpStatus;

/**
 * Refer to:
 * https://github.com/spring-projects/spring-boot/tree/v1.2.5.RELEASE/spring-boot-samples/spring-boot-sample-tomcat-multi-connectors
 * http://docs.spring.io/spring-boot/docs/current/reference/html/howto-embedded-servlet-containers.html
 *
 * @author Tomasz
 */
@Configuration
public class ContainerCustomisation {

    @Bean
    public int port() {
        return SocketUtils.findAvailableTcpPort();
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory tomcatFactory = new TomcatEmbeddedServletContainerFactory();
        tomcatFactory.addAdditionalTomcatConnectors(createSslConnector());
        LogbackValve logbackValve = new LogbackValve();
        logbackValve.setFilename("src/main/resources/logback-access.xml");
        tomcatFactory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/notfound.html"));
        tomcatFactory.setTomcatContextCustomizers(Arrays.asList(context -> {
            context.setSessionTimeout(30);
        }));

        return tomcatFactory;
    }

    private Connector createSslConnector() {
        try {
            return setPkcKey();
        } catch (IOException ex) {
            throw new IllegalStateException("cant access keystore: [" + "keystore"
                    + "] or truststore: [" + "keystore" + "]", ex);
        }
    }

    private Connector setPkcKey() throws IOException {
        Connector connector = new Connector("org.apache.coyote.http11.Http11Protocol");
        Http11Protocol protocol = (Http11Protocol) connector.getProtocolHandler();
        File keystore = new ClassPathResource("my_pkcs12.pfx").getFile();
        connector.setScheme("https");
        connector.setSecure(true);
        connector.setPort(port());
        protocol.setSSLEnabled(true);
        protocol.setSslProtocol("TLS");
        protocol.setKeystoreFile(keystore.getAbsolutePath());
        protocol.setKeystorePass("mypass");
        protocol.setKeystoreType("PKCS12");
        protocol.setClientAuth("false");
        return connector;
    }

}
