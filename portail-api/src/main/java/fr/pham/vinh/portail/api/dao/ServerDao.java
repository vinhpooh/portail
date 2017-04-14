package fr.pham.vinh.portail.api.dao;

import fr.pham.vinh.portail.api.filter.ServerFilter;
import fr.pham.vinh.portail.commons.dto.Server;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Server dao.
 * Created by Vinh PHAM on 04/04/2017.
 */
@Component
public class ServerDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerDao.class);

    @Autowired
    private DataSource datasource;

    /**
     * Find all servers matching filter.
     *
     * @param filter the filter
     * @return A list of servers
     */
    public List<Server> findServers(ServerFilter filter) {
        try (Connection connection = datasource.getConnection()) {
            // TODO : faire la requête
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT name, hostname FROM test WHERE 1=1");

            if (StringUtils.isNotBlank(filter.getProduct())) {
                sql.append(" AND product='").append(filter.getProduct()).append("'");
            }
            if (StringUtils.isNotBlank(filter.getVersion())) {
                sql.append(" AND version='").append(filter.getVersion()).append("'");
            }
            if (StringUtils.isNotBlank(filter.getEnvironment())) {
                sql.append(" AND environment='").append(filter.getEnvironment()).append("'");
            }

            try (PreparedStatement pstmt = connection.prepareStatement(sql.toString())) {
                try (ResultSet resultSet = pstmt.executeQuery()) {

                    List<Server> servers = new ArrayList<>();
                    Server server;
                    while (resultSet.next()) {
                        server = new Server();
                        server.setName(resultSet.getString(1));
                        server.setHostname(resultSet.getString(2));
                        servers.add(server);
                    }
                    return servers;
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
