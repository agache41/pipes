package io.github.agache41.ormpipes.pipes.zip.zipArchive;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * The interface Zips.
 * </pre>
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Zips {
    /**
     * <pre>
     * Value zip [ ].
     * </pre>
     *
     * @return the zip [ ]
     */
    Zip[] value();

    /**
     * <pre>
     * The interface Archives.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Archives {
        /**
         * <pre>
         * Value zip . archive [ ].
         * </pre>
         *
         * @return the zip . archive [ ]
         */
        Zip.Archive[] value();
    }

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
         * Value zip . entry [ ].
         * </pre>
         *
         * @return the zip . entry [ ]
         */
        Zip.Entry[] value();
    }
}
