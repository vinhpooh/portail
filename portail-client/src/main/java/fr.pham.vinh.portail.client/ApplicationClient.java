package fr.pham.vinh.portail.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.pham.vinh.portail.client.client.WebClient;
import fr.pham.vinh.portail.client.inventory.InventoryGenerator;
import fr.pham.vinh.portail.commons.dto.Server;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create an inventory by requesting servers informations.
 * Created by Vinh PHAM on 07/04/2017.
 */
public class ApplicationClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationClient.class);
    private static final String URI_ARG = "uri";
    private static final String PATH_ARG = "path";
    private static final String PRODUCT_PARAMETER = "product";
    private static final String VERSION_PARAMETER = "version";
    private static final String ENVIRONMENT_PARAMETER = "environment";
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
            throw new RuntimeException("Missing program arguments..");
        }
        LOGGER.debug("Uri is {}", uri);
        LOGGER.debug("Path is {}", path);

        // Get parameters from uri
        Map<String, String> parameters = new HashMap<>();
        try {
            List<NameValuePair> nameValuePairs = URLEncodedUtils.parse(new URI(uri), DEFAULT_CHARSET);
            nameValuePairs.forEach(nameValuePair -> parameters.put(nameValuePair.getName(), nameValuePair.getValue()));
        } catch (URISyntaxException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        // Execute the http request
        LOGGER.debug("Execute http request");
        WebClient webClient = new WebClient();
        String response = webClient.get(uri);
        LOGGER.debug("Response is {}", response);

        // Parse the response
        Gson parser = new Gson();
        Type listType = new TypeToken<ArrayList<Server>>() {
        }.getType();
        List<Server> servers = parser.fromJson(response, listType);

        // Generate inventory
        String product = parameters.getOrDefault(PRODUCT_PARAMETER, "defaultProduct");
        String version = parameters.getOrDefault(VERSION_PARAMETER, "defaultVersion");
        String environment = parameters.getOrDefault(ENVIRONMENT_PARAMETER, "defaultEnvironment");
        String inventoryPath = path + "/host_" + product + "_" + version + "_" + environment;

        List<String> lines = new ArrayList<>();
        servers.forEach(server -> lines.add(server.getName() + " ansible_host=" + server.getHostname()));

        InventoryGenerator.writeInventory(inventoryPath, lines, DEFAULT_CHARSET);
        LOGGER.debug("Generated inventory {}", inventoryPath);
    }

}
