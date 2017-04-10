package fr.pham.vinh.portail.commons;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Server request.
 * Created by Vinh PHAM on 04/04/2017.
 */
public class ServerRequest {

    @NotNull(message = "{server.request.product.notnull}")
    @Size(min = 1, message = "{server.request.product.size}")
    private String product;

    @NotNull(message = "{server.request.version.notnull}")
    @Size(min = 1, message = "{server.request.version.size}")
    private String version;

    @NotNull(message = "{server.request.environment.notnull}")
    @Size(min = 1, message = "{server.request.environment.size}")
    private String environment;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }
}
