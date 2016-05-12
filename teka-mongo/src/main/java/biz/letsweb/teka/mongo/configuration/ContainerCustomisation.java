package biz.letsweb.teka.mongo.configuration;

import java.io.File;
import java.io.IOException;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11Protocol;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.SocketUtils;
import ch.qos.logback.access.tomcat.LogbackValve;
import java.io.InputStream;
import java.util.Arrays;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tomasz
 */
@Configuration
public class ContainerCustomisation {

    private final Logger log = LoggerFactory.getLogger(getClass().getName());

    public static final String SSL_KEY_FILENAME = "my_pkcs12.pfx";

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

        tomcatFactory.setTomcatContextCustomizers(Arrays.asList(context -> {
            context.setSessionTimeout(30);
        }));

        return tomcatFactory;
    }

    private Connector createSslConnector() {
        try {
            return setPkcKey();
        } catch (IOException ex) {
            throw new IllegalStateException("Sorry, can't access ssl key.", ex);
        }
    }

    private Connector setPkcKey() throws IOException {
        Connector connector = new Connector("org.apache.coyote.http11.Http11Protocol");
        Http11Protocol protocol = (Http11Protocol) connector.getProtocolHandler();
        InputStream keystoreInput = new ClassPathResource(SSL_KEY_FILENAME).getInputStream();
        File keyFile = File.createTempFile("my_pkcs12", "pfx");
        FileUtils.copyInputStreamToFile(keystoreInput, keyFile);
        connector.setScheme("https");
        connector.setSecure(true);
        connector.setPort(port());
        protocol.setSSLEnabled(true);
        protocol.setSslProtocol("TLS");
        protocol.setKeystoreFile(keyFile.getAbsolutePath());
        protocol.setKeystorePass("mypass");
        protocol.setKeystoreType("PKCS12");
        protocol.setClientAuth("false");
        return connector;
    }

}
