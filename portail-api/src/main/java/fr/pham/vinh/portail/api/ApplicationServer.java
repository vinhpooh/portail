package fr.pham.vinh.portail.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Start an embedded tomcat that serve the jersey config resource.
 * Created by Vinh PHAM on 04/04/2017.
 */
@SpringBootApplication
public class ApplicationServer {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationServer.class, args);
    }

}
