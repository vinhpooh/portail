package fr.pham.vinh.portail.api;

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
        packages(this.getClass().getPackage().getName() + ".resource");
        packages(this.getClass().getPackage().getName() + ".exception");
    }
}