package fr.pham.vinh.portail.client.inventory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Generate an inventory.
 * Created by Vinh PHAM on 10/04/2017.
 */
public class InventoryGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryGenerator.class);

    /**
     * Write content into a file.
     *
     * @param path    the path of the file
     * @param lines   the content
     * @param charset the charset to use for encoding
     */
    public static void writeInventory(String path, List<String> lines, Charset charset) {
        Path file = Paths.get(path);
        try {
            Files.write(file, lines, charset);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
