package io.github.agache41.ormpipes.pipes.field;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * The interface Fields.
 * </pre>
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Fields {
    /**
     * <pre>
     * Value field [ ].
     * </pre>
     *
     * @return the field [ ]
     */
    Field[] value();

    /**
     * <pre>
     * The interface For each field havings.
     * </pre>
     */
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface ForEachFieldHavings {
        /**
         * <pre>
         * Value field . for each annotated with [ ].
         * </pre>
         *
         * @return the field . for each annotated with [ ]
         */
        Field.forEachAnnotatedWith[] value();
    }
}
