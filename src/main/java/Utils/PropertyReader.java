package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader
{
    public static String getProperty(String key) throws IOException {
        String configFilePath = "config.properties";
        BufferedReader reader = new BufferedReader(new FileReader(configFilePath));
        Properties properties = new Properties();
        properties.load(reader);
        return properties.getProperty(key);
    }
}
