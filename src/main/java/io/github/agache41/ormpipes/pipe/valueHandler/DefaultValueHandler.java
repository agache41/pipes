package io.github.agache41.ormpipes.pipe.valueHandler;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Handler for Default values
 * </pre>
 */
public class DefaultValueHandler implements ValueHandler {

    private static final Map<Class<?>, Object> defaultClassValuesMap = new HashMap<>();

    static {
        defaultClassValuesMap.put(String.class,
                                  "");
        defaultClassValuesMap.put(Integer.class,
                                  Integer.valueOf(0));
        defaultClassValuesMap.put(Long.class,
                                  Long.valueOf(0L));
        defaultClassValuesMap.put(Double.class,
                                  Double.valueOf(0));
        defaultClassValuesMap.put(Float.class,
                                  Float.valueOf(0));
        defaultClassValuesMap.put(BigDecimal.class,
                                  BigDecimal.valueOf(0));
        defaultClassValuesMap.put(Date.class,
                                  new Date(0));
        defaultClassValuesMap.put(LocalDate.class,
                                  LocalDate.MIN);
        defaultClassValuesMap.put(LocalTime.class,
                                  LocalTime.MIN);
        defaultClassValuesMap.put(LocalDateTime.class,
                                  LocalDateTime.MIN);
        defaultClassValuesMap.put(java.sql.Date.class,
                                  java.sql.Date.valueOf(LocalDate.MIN));
        defaultClassValuesMap.put(Timestamp.class,
                                  Timestamp.valueOf(LocalDateTime.MIN));
    }

    /**
     * <pre>
     * Add a default value.
     * </pre>
     *
     * @param clazz the clazz
     * @param value the value
     */
    public void addDefaultValue(Class<?> clazz,
                                Object value) {
        defaultClassValuesMap.put(clazz,
                                  value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <Output> Output handleValue(Class<Output> outputClass,
                                       Object inputValue) {

        return (Output) defaultClassValuesMap.getOrDefault(outputClass,
                                                           null);

    }
}
