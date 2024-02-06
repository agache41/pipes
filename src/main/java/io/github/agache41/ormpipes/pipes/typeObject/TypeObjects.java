package io.github.agache41.ormpipes.pipes.typeObject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * The interface Type objects.
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
public @interface TypeObjects {
    /**
     * <pre>
     * Value type object [ ].
     * </pre>
     *
     * @return the type object [ ]
     */
    TypeObject[] value();

    /**
     * <pre>
     * The interface To upper cases.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface toUpperCases {
        /**
         * <pre>
         * Value type object . to string [ ].
         * </pre>
         *
         * @return the type object . to string [ ]
         */
        TypeObject.toString[] value();
    }

}
