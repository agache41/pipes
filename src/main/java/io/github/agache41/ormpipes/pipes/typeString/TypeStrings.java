package io.github.agache41.ormpipes.pipes.typeString;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * The interface Type strings.
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
public @interface TypeStrings {
    /**
     * <pre>
     * Value type string [ ].
     * </pre>
     *
     * @return the type string [ ]
     */
    TypeString[] value();

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
         * Value type string . to upper case [ ].
         * </pre>
         *
         * @return the type string . to upper case [ ]
         */
        TypeString.toUpperCase[] value();
    }

    /**
     * <pre>
     * The interface To lower cases.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface toLowerCases {
        /**
         * <pre>
         * Value type string . to lower case [ ].
         * </pre>
         *
         * @return the type string . to lower case [ ]
         */
        TypeString.toLowerCase[] value();
    }

    /**
     * <pre>
     * The interface Substrings.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface substrings {
        /**
         * <pre>
         * Value type string . substring [ ].
         * </pre>
         *
         * @return the type string . substring [ ]
         */
        TypeString.substring[] value();
    }

    /**
     * <pre>
     * The interface Replaces.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface replaces {
        /**
         * <pre>
         * Value type string . replace [ ].
         * </pre>
         *
         * @return the type string . replace [ ]
         */
        TypeString.replace[] value();
    }

    /**
     * <pre>
     * The interface Replace alls.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface replaceAlls {
        /**
         * <pre>
         * Value type string . replace all [ ].
         * </pre>
         *
         * @return the type string . replace all [ ]
         */
        TypeString.replaceAll[] value();
    }

    /**
     * <pre>
     * The interface Trims.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface trims {
        /**
         * <pre>
         * Value type string . trim [ ].
         * </pre>
         *
         * @return the type string . trim [ ]
         */
        TypeString.trim[] value();
    }

    /**
     * <pre>
     * The interface Quoteds.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface quoteds {
        /**
         * <pre>
         * Value type string . quoted [ ].
         * </pre>
         *
         * @return the type string . quoted [ ]
         */
        TypeString.quoted[] value();
    }

    /**
     * <pre>
     * The interface Nullables.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface nullables {
        /**
         * <pre>
         * Value type string . nullable [ ].
         * </pre>
         *
         * @return the type string . nullable [ ]
         */
        TypeString.nullable[] value();
    }

    /**
     * <pre>
     * The interface Values.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface values {
        /**
         * <pre>
         * Value type string . value [ ].
         * </pre>
         *
         * @return the type string . value [ ]
         */
        TypeString.value[] value();
    }

    /**
     * <pre>
     * The interface Io liness.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface IOLiness {
        /**
         * <pre>
         * Value type string . io stream lines [ ].
         * </pre>
         *
         * @return the type string . io stream lines [ ]
         */
        TypeString.IOStreamLines[] value();
    }

    /**
     * <pre>
     * The interface Arrays.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Arrays {
        /**
         * <pre>
         * Value type string . array [ ].
         * </pre>
         *
         * @return the type string . array [ ]
         */
        TypeString.Array[] value();
    }

    /**
     * <pre>
     * The interface Lists.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Lists {
        /**
         * <pre>
         * Value type string . list [ ].
         * </pre>
         *
         * @return the type string . list [ ]
         */
        TypeString.List[] value();
    }

    /**
     * <pre>
     * The interface Sets.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Sets {
        /**
         * <pre>
         * Value type string . set [ ].
         * </pre>
         *
         * @return the type string . set [ ]
         */
        TypeString.Set[] value();
    }

    /**
     * <pre>
     * The interface Streams.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Streams {
        /**
         * <pre>
         * Value type string . stream [ ].
         * </pre>
         *
         * @return the type string . stream [ ]
         */
        TypeString.Stream[] value();
    }

    /**
     * <pre>
     * The interface Cell values.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface CellValues {
        /**
         * <pre>
         * Value type string . cell value [ ].
         * </pre>
         *
         * @return the type string . cell value [ ]
         */
        TypeString.cellValue[] value();
    }
}
