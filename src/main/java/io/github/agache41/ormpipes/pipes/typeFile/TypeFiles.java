package io.github.agache41.ormpipes.pipes.typeFile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * The interface Type files.
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
public @interface TypeFiles {
    /**
     * <pre>
     * Value type file [ ].
     * </pre>
     *
     * @return the type file [ ]
     */
    TypeFile[] value();

    /**
     * <pre>
     * The interface News.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface News {
        /**
         * <pre>
         * Value type file . new [ ].
         * </pre>
         *
         * @return the type file . new [ ]
         */
        TypeFile.New[] value();
    }


    /**
     * <pre>
     * The interface New resources.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface NewResources {
        /**
         * <pre>
         * Value type file . new resource [ ].
         * </pre>
         *
         * @return the type file . new resource [ ]
         */
        TypeFile.NewResource[] value();
    }

    /**
     * <pre>
     * The interface New temporarys.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface NewTemporarys {
        /**
         * <pre>
         * Value type file . new temporary [ ].
         * </pre>
         *
         * @return the type file . new temporary [ ]
         */
        TypeFile.NewTemporary[] value();
    }


    /**
     * <pre>
     * The interface Mkdirss.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface mkdirss {
        /**
         * <pre>
         * Value type file . mkdirs [ ].
         * </pre>
         *
         * @return the type file . mkdirs [ ]
         */
        TypeFile.mkdirs[] value();
    }

    /**
     * <pre>
     * The interface List filess.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface listFiless {
        /**
         * <pre>
         * Value type file . list files [ ].
         * </pre>
         *
         * @return the type file . list files [ ]
         */
        TypeFile.listFiles[] value();
    }

    /**
     * <pre>
     * The interface Filters.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface filters {
        /**
         * <pre>
         * Value type file . filter [ ].
         * </pre>
         *
         * @return the type file . filter [ ]
         */
        TypeFile.filter[] value();
    }
}
