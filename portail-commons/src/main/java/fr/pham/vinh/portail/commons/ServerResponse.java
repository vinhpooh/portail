package fr.pham.vinh.portail.commons;

import fr.pham.vinh.portail.commons.dto.Server;

import java.util.List;

/**
 * Server response.
 * Created by Vinh PHAM on 04/04/2017.
 */
public class ServerResponse {

    private List<Server> servers;

    public List<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }
}
