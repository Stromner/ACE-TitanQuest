package ace.titan.quest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestUtils {
    private static final Logger log = LoggerFactory.getLogger(TestUtils.class);
    private final static String PROPERTIES_PATH = "application.properties";
    private static Properties properties;

    public static Properties getPropertyFile() {
        if (properties != null) {
            return properties;
        }

        properties = new Properties();
        InputStream inputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(PROPERTIES_PATH);
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            log.error("Not able to find properties file '{}'", PROPERTIES_PATH);
            throw new RuntimeException(e.getCause());
        }

        return properties;
    }
}
