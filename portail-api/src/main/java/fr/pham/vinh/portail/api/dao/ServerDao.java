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
            StringBuilder sql = new StringBuilder("SELECT sl.nom, sl.host FROM serveur_logique sl");
            // serveur_logique --> brique_technique
            sql.append(" INNER JOIN brique_technique bt on bt.serveur_logique_id=sl.id");
            // brique_technique --> instance_brique_technique
            sql.append(" INNER JOIN instance_brique_technique ibt on ibt.brique_technique_id=bt.id");
            // instance_brique_technique --> instance
            sql.append(" INNER JOIN instance i on i.nom=ibt.instance_nom");
            // instance --> environnement
            sql.append(" INNER JOIN environnement e on e.nom=i.environnement_nom");

            // produit --> environnement
            sql.append(" INNER JOIN produit p on p.id=e.produit_id");
            // version_produit --> produit
            sql.append(" INNER JOIN version_produit vp on vp.produit_id=p.id");

            // Where clause
            sql.append(" WHERE 1=1");

            if (StringUtils.isNotBlank(filter.getProduct())) {
                sql.append(" AND p.application_nom='").append(filter.getProduct()).append("'");
            }
            if (StringUtils.isNotBlank(filter.getVersion())) {
                sql.append(" AND vp.num='").append(filter.getVersion()).append("'");
            }
            if (StringUtils.isNotBlank(filter.getEnvironment())) {
                sql.append(" AND e.nom='").append(filter.getEnvironment()).append("'");
            }

            LOGGER.debug("Sql request : {}", sql.toString());
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
