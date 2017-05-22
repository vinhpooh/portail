package fr.pham.vinh.portail.api.resource;

import fr.pham.vinh.portail.api.dao.ServerDao;
import fr.pham.vinh.portail.api.filter.ServerFilter;
import fr.pham.vinh.portail.commons.dto.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Server resource.
 */
@Path("/servers")
public class ServerResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerResource.class);

    @Autowired
    private ServerDao serverDao;

    /**
     * Find servers corresponding the request.
     *
     * @param product     product filter
     * @param version     version filter
     * @param environment environment filter
     * @return the list of servers
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("product") String product,
                               @QueryParam("version") String version,
                               @QueryParam("environment") String environment) {
        LOGGER.debug("Get servers");
        ServerFilter filter = new ServerFilter();
        filter.setProduct(product);
        filter.setVersion(version);
        filter.setEnvironment(environment);
        List<Server> servers = serverDao.findServers(filter);
        return Response.ok(servers).build();
    }
}