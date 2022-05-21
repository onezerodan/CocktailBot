package onezerodan.cocktailbot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {

    static Logger log = LoggerFactory.getLogger(PropertiesLoader.class);
    public  Properties getProperties(String className) {

        String propPath;
        Properties properties = new Properties();

        switch (className) {
            case "parser" :
                try {
                    propPath = PropertiesLoader.class.getClassLoader().getResource("parser.properties").getPath();
                    properties.load(new FileInputStream(propPath));
                } catch (NullPointerException | IOException e) {
                    log.error("Properties for parser not found.");
                    return null;
                }
                return properties;

            case "bot":
                try {
                    propPath = PropertiesLoader.class.getClassLoader().getResource("bot.properties").getPath();
                    properties.load(new FileInputStream(propPath));
                } catch (NullPointerException | IOException e) {
                    log.error("Properties for bot not found.");
                    return null;
                }
                return properties;

        }

        return null;





    }
}
