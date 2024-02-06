package io.github.agache41.ormpipes.config;


/**
 * <pre>
 * The enum Settings.
 * </pre>
 */
public enum Settings {
    /**
     * <pre>
     * Numeric integer format settings.
     * </pre>
     */
    NUMERIC_INTEGER_FORMAT("pipes.integer.format"),
    /**
     * <pre>
     * Numeric floating point format settings.
     * </pre>
     */
    NUMERIC_FLOATING_POINT_FORMAT("pipes.floatingPoint.format"),
    /**
     * <pre>
     * Date time format settings.
     * </pre>
     */
    DATE_TIME_FORMAT("pipes.dateTime.format"),
    /**
     * <pre>
     * Language tag settings.
     * </pre>
     */
    LANGUAGE_TAG("pipes.languageTag"),
    /**
     * <pre>
     * Zone id settings.
     * </pre>
     */
    ZONE_ID("pipes.zone_Id");

    /**
     * <pre>
     * The constant DEFAULT.
     * </pre>
     */
    public static final String DEFAULT = Constants.DEFAULT;

    private final String key;

    Settings(String key) {
        this.key = key;
    }

    /**
     * <pre>
     * Gets key.
     * </pre>
     *
     * @return the key
     */
    public String getKey() {
        return this.key;
    }
}
