package io.github.agache41.ormpipes.pipes.collectors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * The interface Collectorss.
 * </pre>
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Collectorss {
    /**
     * <pre>
     * Value collectors [ ].
     * </pre>
     *
     * @return the collectors [ ]
     */
    Collectors[] value();

    /**
     * <pre>
     * The interface To lists.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface toLists {
        /**
         * <pre>
         * Value collectors . to list [ ].
         * </pre>
         *
         * @return the collectors . to list [ ]
         */
        Collectors.toList[] value();
    }

    /**
     * <pre>
     * The interface To linked lists.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface toLinkedLists {
        /**
         * <pre>
         * Value collectors . to linked list [ ].
         * </pre>
         *
         * @return the collectors . to linked list [ ]
         */
        Collectors.toLinkedList[] value();
    }

    /**
     * <pre>
     * The interface To sets.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface toSets {
        /**
         * <pre>
         * Value collectors . to set [ ].
         * </pre>
         *
         * @return the collectors . to set [ ]
         */
        Collectors.toSet[] value();
    }

    /**
     * <pre>
     * The interface To tree sets.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface toTreeSets {
        /**
         * <pre>
         * Value collectors . to tree set [ ].
         * </pre>
         *
         * @return the collectors . to tree set [ ]
         */
        Collectors.toTreeSet[] value();
    }

    /**
     * <pre>
     * The interface To maps.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface toMaps {
        /**
         * <pre>
         * Value collectors . to map [ ].
         * </pre>
         *
         * @return the collectors . to map [ ]
         */
        Collectors.toMap[] value();
    }

    /**
     * <pre>
     * The interface Joinings.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface joinings {
        /**
         * <pre>
         * Value collectors . joining [ ].
         * </pre>
         *
         * @return the collectors . joining [ ]
         */
        Collectors.joining[] value();
    }
}
