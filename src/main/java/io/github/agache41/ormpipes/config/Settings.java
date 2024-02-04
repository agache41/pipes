package io.github.agache41.ormpipes.config;



public enum Settings {
    NUMERIC_INTEGER_FORMAT("pipes.integer.format"),
    NUMERIC_FLOATING_POINT_FORMAT("pipes.floatingPoint.format"),
    DATE_TIME_FORMAT("pipes.dateTime.format"),
    LANGUAGE_TAG("pipes.languageTag"),
    ZONE_ID("pipes.zone_Id");

    public static final String DEFAULT = Annotations.DEFAULT;

    private final String key;

    Settings(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
