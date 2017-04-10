package fr.pham.vinh.portail.api.filter;

/**
 * Server filter.
 * Created by Vinh PHAM on 04/04/2017.
 */
public class ServerFilter {

    private String product;

    private String version;

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
