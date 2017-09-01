package fr.pham.vinh.portail.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.pham.vinh.portail.client.client.WebClient;
import fr.pham.vinh.portail.client.inventory.InventoryGenerator;
import fr.pham.vinh.portail.commons.dto.Server;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create an inventory file by requesting servers informations.
 * Created by Vinh PHAM on 07/04/2017.
 */
public class ApplicationClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationClient.class);
    private static final String URI_ARG = "uri";
    private static final String PATH_ARG = "path";
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    public static void main(String[] args) {
        // Get program arguments
        Map<String, String> map = new HashMap<>();
        for (String arg : args) {
            String[] array = arg.split("=", 2);
            map.put(array[0].toLowerCase(), array[1]);
        }
        String uri = map.get(URI_ARG);
        String path = map.get(PATH_ARG);

        // Verify program arguments
        if (StringUtils.isBlank(uri) || StringUtils.isBlank(path)) {
            throw new RuntimeException("Missing program arguments. \"uri\" and \"path\" parameters are mandatory (e.g. uri=http://localhost:8080/portail-api/servers?product=XXX&version=YYY&environment=ZZZ path=AAA)");
        }
        LOGGER.debug("{} is {}", URI_ARG, uri);
        LOGGER.debug("{} is {}", PATH_ARG, path);

        // Execute the http request
        LOGGER.debug("Execute http request {}", uri);
        WebClient webClient = new WebClient();
        String response = webClient.get(uri);
        LOGGER.debug("Response is {}", response);

        // Parse the response
        Gson parser = new Gson();
        Type listType = new TypeToken<ArrayList<Server>>() {
        }.getType();
        List<Server> servers = parser.fromJson(response, listType);

        // Generate inventory
        InventoryGenerator.createInventory(servers, path, DEFAULT_CHARSET);
        LOGGER.debug("Generated inventory {}", path);
    }

}
