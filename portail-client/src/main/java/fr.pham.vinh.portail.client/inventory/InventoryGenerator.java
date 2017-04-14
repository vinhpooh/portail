package fr.pham.vinh.portail.client.inventory;

import fr.pham.vinh.portail.commons.dto.Server;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Inventory generator.
 * Created by Vinh PHAM on 10/04/2017.
 */
public class InventoryGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryGenerator.class);

    /**
     * Create an inventory file with the list of servers supplied.
     * The file is created with the path supplied.
     *
     * @param servers the servers' list
     * @param path    file path to create
     * @param charset encoding to use
     */
    public static void createInventory(List<Server> servers, String path, Charset charset) {
        StringBuffer data = new StringBuffer();

        // Sort and group by server's name
        Map<String, List<Server>> sortedServers = servers.stream()
                .sorted(Comparator.comparing(Server::getName))
                .collect(Collectors.groupingBy(Server::getName, LinkedHashMap::new, Collectors.toList()));

        // Generate datas to write
        sortedServers.forEach((group, members) -> {
            if (members.size() == 1) {
                // Create a single member
                members.forEach(server -> data
                        .append(server.getName())
                        .append(" ansible_host=")
                        .append(server.getHostname())
                        .append("\n\n"));
            } else {
                // Create a group to handle multiple members
                data.append("[").append(group).append("]\n");
                // Create group's members
                for (int i = 0; i < members.size(); i++) {
                    data.append(members.get(i).getName())
                            .append("_").append(i)
                            .append(" ansible_host=")
                            .append(members.get(i).getHostname())
                            .append("\n");
                }
                data.append("\n");
            }
        });

        // Write data into file
        writeInventory(path, data.toString(), charset);
    }

    /**
     * Write data into a file.
     *
     * @param path    the path of the file
     * @param data    the content
     * @param charset the charset to use for encoding
     */
    private static void writeInventory(String path, String data, Charset charset) {
        File file = new File(path);
        try {
            FileUtils.writeStringToFile(file, data, charset);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
