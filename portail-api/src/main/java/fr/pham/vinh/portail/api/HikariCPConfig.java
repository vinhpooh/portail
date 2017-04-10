package fr.pham.vinh.portail.api;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr.pham.vinh.portail.api.dao.ServerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Provide the DataSource.
 * Created by Vinh PHAM on 07/04/2017.
 */
@Configuration
public class HikariCPConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(HikariCPConfig.class);

    private final static DataSource datasource;

    static {
        Properties properties = new Properties();
        try (InputStream in = ServerDao.class.getClassLoader().getResourceAsStream("hikaricp.properties")) {
            properties.load(in);

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(properties.getProperty("datasource.url"));
            config.setUsername(properties.getProperty("datasource.username"));
            config.setPassword(properties.getProperty("datasource.password"));
            // The maximum number of connections, idle or busy, that can be present in the pool.
            config.setMaximumPoolSize(10);
            // The default auto-commit behavior of connections returned from the pool. Default: true
            config.setAutoCommit(false);
            datasource = new HikariDataSource(config);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Return the datasource.
     *
     * @return the instance
     */
    @Bean
    public static DataSource getDataSource() {
        return datasource;
    }

}
