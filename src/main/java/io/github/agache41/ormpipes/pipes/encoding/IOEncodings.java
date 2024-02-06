package io.github.agache41.ormpipes.pipes.encoding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * The interface Io encodings.
 * </pre>
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IOEncodings {
    /**
     * <pre>
     * Value io encoding [ ].
     * </pre>
     *
     * @return the io encoding [ ]
     */
    IOEncoding[] value();

    /**
     * <pre>
     * The interface Charsets.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Charsets {
        /**
         * <pre>
         * Value io encoding . charset [ ].
         * </pre>
         *
         * @return the io encoding . charset [ ]
         */
        IOEncoding.Charset[] value();
    }

    /**
     * <pre>
     * The interface Bo ms.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface BOMs {
        /**
         * <pre>
         * Value io encoding . bom [ ].
         * </pre>
         *
         * @return the io encoding . bom [ ]
         */
        IOEncoding.BOM[] value();
    }

    /**
     * <pre>
     * The interface Automatics.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Automatics {
        /**
         * <pre>
         * Value io encoding . automatic [ ].
         * </pre>
         *
         * @return the io encoding . automatic [ ]
         */
        IOEncoding.Automatic[] value();
    }
}
