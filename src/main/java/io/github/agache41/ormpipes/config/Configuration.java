package io.github.agache41.ormpipes.config;

import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import org.jboss.logging.Logger;

import java.io.InputStream;
import java.util.EnumMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * <pre>
 * The type Configuration.
 * </pre>
 */
public class Configuration {
    private static final Logger logger = Logger.getLogger(AnnotablePipe.class);
    private static final String configurationFileName = "pipes.config.properties";
    private static Supplier<Map<Settings, String>> configurationProvider = new FileConfigurationProvider(configurationFileName);
    private static Configuration instance;
    private final EnumMap<Settings, String> configurationValues;

    /**
     * <pre>
     * Instantiates a new Configuration.
     * </pre>
     *
     * @param configValuesSupplier the config values supplier
     */
    public Configuration(Supplier<Map<Settings, String>> configValuesSupplier) {
        this.configurationValues = new EnumMap<>(Settings.class);
        this.configurationValues.putAll(configValuesSupplier.get());
    }

    /**
     * <pre>
     * Sets configuration provider.
     * </pre>
     *
     * @param configurationProvider the configuration provider
     */
    public static void setConfigurationProvider(Supplier<Map<Settings, String>> configurationProvider) {
        Configuration.configurationProvider = configurationProvider;
    }

    /**
     * <pre>
     * Gets instance.
     * </pre>
     *
     * @return the instance
     */
    public static synchronized Configuration getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new Configuration(configurationProvider);
        return instance;
    }

    /**
     * <pre>
     * Get string.
     * </pre>
     *
     * @param key the key
     * @return the string
     */
    public String get(Settings key) {
        return this.configurationValues.get(key);
    }

    /**
     * <pre>
     * Set a string.
     * </pre>
     *
     * @param key   the key
     * @param value the value
     */
    public void set(Settings key,
                    String value) {
        this.configurationValues.put(key, value);
    }

    /**
     * <pre>
     * The type File configuration provider.
     * </pre>
     */
    public static class FileConfigurationProvider implements Supplier<Map<Settings, String>> {

        private final String fileName;

        private FileConfigurationProvider(String fileName) {
            this.fileName = fileName;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Map<Settings, String> get() {
            final EnumMap<Settings, String> configurationValues = new EnumMap<>(Settings.class);
            try {
                final InputStream resource = this.getClass()
                                                 .getClassLoader()
                                                 .getResourceAsStream(this.fileName);
                if (resource == null) {
                    logger.infof("%s is missing, no default configuration", this.fileName);
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
