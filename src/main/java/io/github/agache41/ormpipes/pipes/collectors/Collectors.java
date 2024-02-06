package io.github.agache41.ormpipes.pipes.collectors;


import io.github.agache41.annotator.annotations.Extends;
import io.github.agache41.ormpipes.config.Constants;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.DualPipe;

import java.lang.annotation.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * <pre>
 * The interface Collectors.
 * </pre>
 */
@Repeatable(Collectorss.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Extends({DualPipe.class})
public @interface Collectors {
    /**
     * <pre>
     * The interface To list.
     * </pre>
     */
    @Repeatable(Collectorss.toLists.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class})
    @interface toList {
        /**
         * <pre>
         * Enabled on string [ ].
         * </pre>
         *
         * @return the string [ ]
         */
        String[] enabledOn() default {"read", "write"};

        /**
         * <pre>
         * Read class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<Collectors.toList, Stream<?>, List<?>>> read() default CollectorPipes.ToList.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<?, Collection<?>, Stream<?>>> write() default CollectorPipes.CollectionToStream.class;

        /**
         * <pre>
         * View string.
         * </pre>
         *
         * @return the string
         */
        String view() default Constants.DEFAULT;
    }

    /**
     * <pre>
     * The interface To linked list.
     * </pre>
     */
    @Repeatable(Collectorss.toLinkedLists.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class})
    @interface toLinkedList {
        /**
         * <pre>
         * Enabled on string [ ].
         * </pre>
         *
         * @return the string [ ]
         */
        String[] enabledOn() default {"read", "write"};

        /**
         * <pre>
         * Read class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<Collectors.toLinkedList, Stream<?>, LinkedList<?>>> read() default CollectorPipes.ToLinkedList.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<?, Collection<?>, Stream<?>>> write() default CollectorPipes.CollectionToStream.class;

        /**
         * <pre>
         * View string.
         * </pre>
         *
         * @return the string
         */
        String view() default Constants.DEFAULT;
    }

    /**
     * <pre>
     * The interface To set.
     * </pre>
     */
    @Repeatable(Collectorss.toSets.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class})
    @interface toSet {
        /**
         * <pre>
         * Enabled on string [ ].
         * </pre>
         *
         * @return the string [ ]
         */
        String[] enabledOn() default {"read", "write"};

        /**
         * <pre>
         * Read class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<Collectors.toSet, Stream<?>, Set<?>>> read() default CollectorPipes.ToSet.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<?, Collection<?>, Stream<?>>> write() default CollectorPipes.CollectionToStream.class;

        /**
         * <pre>
         * View string.
         * </pre>
         *
         * @return the string
         */
        String view() default Constants.DEFAULT;
    }

    /**
     * <pre>
     * The interface To tree set.
     * </pre>
     */
    @Repeatable(Collectorss.toTreeSets.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class})
    @interface toTreeSet {
        /**
         * <pre>
         * Enabled on string [ ].
         * </pre>
         *
         * @return the string [ ]
         */
        String[] enabledOn() default {"read", "write"};

        /**
         * <pre>
         * Read class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<Collectors.toTreeSet, Stream<?>, TreeSet<?>>> read() default CollectorPipes.ToTreeSet.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<?, Collection<?>, Stream<?>>> write() default CollectorPipes.CollectionToStream.class;

        /**
         * <pre>
         * View string.
         * </pre>
         *
         * @return the string
         */
        String view() default Constants.DEFAULT;
    }

    /**
     * <pre>
     * The interface To map.
     * </pre>
     */
    @Repeatable(Collectorss.toMaps.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class})
    @interface toMap {

        /**
         * The name of the field (accessor) to be used as key.
         *
         * @return the string
         */
        String key();

        /**
         * The Class containing the field. Can actually be the type of data in the Stream or a superclass.
         *
         * @return the class
         */
        Class<?> clazz();

        /**
         * <pre>
         * Enabled on string [ ].
         * </pre>
         *
         * @return the string [ ]
         */
        String[] enabledOn() default {"read", "write"};

        /**
         * <pre>
         * Read class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<Collectors.toMap, Stream<?>, Map<?, ?>>> read() default CollectorPipes.ToMap.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<?, Map<?, ?>, Stream<?>>> write() default CollectorPipes.MapToStream.class;

        /**
         * <pre>
         * View string.
         * </pre>
         *
         * @return the string
         */
        String view() default Constants.DEFAULT;
    }

    /**
     * <pre>
     * The interface Joining.
     * </pre>
     */
    @Repeatable(Collectorss.joinings.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class})
    @interface joining {

        /**
         * The separator to use.
         *
         * @return the string
         */
        String separator();

        /**
         * <pre>
         * Enabled on string [ ].
         * </pre>
         *
         * @return the string [ ]
         */
        String[] enabledOn() default {"read", "write"};

        /**
         * <pre>
         * Read class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<Collectors.joining, Stream<String>, String>> read() default CollectorPipes.JoinStream.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<Collectors.joining, String, Stream<String>>> write() default CollectorPipes.SplitStream.class;

        /**
         * <pre>
         * View string.
         * </pre>
         *
         * @return the string
         */
        String view() default Constants.DEFAULT;
    }
}
