package io.github.agache41.ormpipes.config;

import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.EnumMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Configuration {
    private static final Logger logger = LogManager.getLogger(AnnotablePipe.class);
    private static final String configurationFileName = "pipes.config.properties";
    private static Supplier<Map<Settings, String>> configurationProvider = new FileConfigurationProvider(configurationFileName);
    private static Configuration instance;
    private final EnumMap<Settings, String> configurationValues;

    public Configuration(Supplier<Map<Settings, String>> configValuesSupplier) {
        this.configurationValues = new EnumMap<>(Settings.class);
        this.configurationValues.putAll(configValuesSupplier.get());
    }

    public static void setConfigurationProvider(Supplier<Map<Settings, String>> configurationProvider) {
        Configuration.configurationProvider = configurationProvider;
    }

    public static synchronized Configuration getInstance() {
        if (instance != null) return instance;
        instance = new Configuration(configurationProvider);
        return instance;
    }

    public String get(Settings key) {
        return this.configurationValues.get(key);
    }

    public void set(Settings key, String value) {
        this.configurationValues.put(key, value);
    }

    public static class FileConfigurationProvider implements Supplier<Map<Settings, String>> {

        private final String fileName;

        private FileConfigurationProvider(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public Map<Settings, String> get() {
            final EnumMap<Settings, String> configurationValues = new EnumMap<>(Settings.class);
            try {
                final InputStream resource = this.getClass()
                                                 .getClassLoader()
                                                 .getResourceAsStream(this.fileName);
                if (resource == null) {
                    logger.info("{} is missing, no default configuration", this.fileName);
                    return configurationValues;
                }
                final Properties properties = new Properties();

                properties.load(resource);
                Stream.of(Settings.values())
                      .forEach(setting -> configurationValues.put(setting, properties.getProperty(setting.getKey(), Settings.DEFAULT)));
            } catch (Exception e) {
                logger.error(e);
            } finally {
                return configurationValues;
            }
        }
    }
}
