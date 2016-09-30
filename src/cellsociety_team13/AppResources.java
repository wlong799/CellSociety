package cellsociety_team13;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class AppResources {
    private static final String RESOURCE_PACKAGE = "resources/";
    private static final String RESOURCE_FILETYPE = ".properties";
    private static final String FILENAME_ERROR_MESSAGE = "Resource not found with specified filename: ";
    private static final String IO_ERROR_MESSAGE = "IO Error when loading resource with filename: ";

    private static Map<String, Properties> propertiesMap = new HashMap<>();

    private AppResources() {
    }

    public static Properties getResourceFile(String filename) {
        if (propertiesMap.containsKey(filename)) {
            return propertiesMap.get(filename);
        }
        try {
            FileInputStream fin = new FileInputStream(RESOURCE_PACKAGE+ filename + RESOURCE_FILETYPE);
            Properties property = new Properties();
            property.load(fin);
            propertiesMap.put(filename, property);
            return property;
        } catch (FileNotFoundException e) {
            System.out.println(FILENAME_ERROR_MESSAGE + filename);
            return null;
        } catch (IOException e) {
            System.out.println(IO_ERROR_MESSAGE + filename);
            return null;
        }
    }
}
