package io.github.agache41.ormpipes.pipes.csv.csvField;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * The interface Csv accessors.
 * </pre>
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CSVAccessors {
    /**
     * <pre>
     * Value csv accessor [ ].
     * </pre>
     *
     * @return the csv accessor [ ]
     */
    CSVAccessor[] value();
}
