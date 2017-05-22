package fr.pham.vinh.portail.api;

import fr.pham.vinh.portail.api.resource.HealthcheckResource;
import fr.pham.vinh.portail.api.resource.ServerResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

/**
 * Provide Jersey application.
 * Created by Vinh PHAM on 04/04/2017.
 */
@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        // scan the resources package for the resources

        // TODO : https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-1.4-Release-Notes#jersey-classpath-scanning-limitations
        // Jersey classpath scanning limitations
        // The change to the layout of executable jars means that a limitation in Jersey’s classpath scanning now affects executable
        // jar files as well as executable war files. To work around the problem, classes that you wish to be scanned by Jersey should
        // be packaged in a jar and included as a dependency in BOOT-INF/lib. The Spring Boot launcher should then be configured
        // to unpack those jars on start up so that Jersey can scan their contents.

        // packages(this.getClass().getPackage().getName() + ".resource");
        // packages(this.getClass().getPackage().getName() + ".exception");
        register(HealthcheckResource.class);
        register(ServerResource.class);
    }
}