package io.github.agache41.ormpipes.pipes.iostream;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * The interface Io streams.
 * </pre>
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IOStreams {
    /**
     * <pre>
     * Value io stream [ ].
     * </pre>
     *
     * @return the io stream [ ]
     */
    IOStream[] value();

    /**
     * <pre>
     * The interface Files.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Files {
        /**
         * <pre>
         * Value io stream . file based [ ].
         * </pre>
         *
         * @return the io stream . file based [ ]
         */
        IOStream.FileBased[] value();
    }
}
