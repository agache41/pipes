package com.orm.pipes.collectors;


import com.orm.annotations.Annotations;
import com.orm.annotations.Extends;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipe.DualPipe;

import java.lang.annotation.*;
import java.util.*;
import java.util.stream.Stream;

@Repeatable(Collectorss.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Extends({DualPipe.class})
public @interface Collectors {
    @Repeatable(Collectorss.toLists.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class})
    @interface toList {
        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Collectors.toList, Stream<?>, List<?>>> read() default CollectorPipes.ToList.class;

        Class<? extends AnnotablePipe<?, Collection<?>, Stream<?>>> write() default CollectorPipes.CollectionToStream.class;

        String view() default Annotations.DEFAULT;
    }

    @Repeatable(Collectorss.toLinkedLists.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class})
    @interface toLinkedList {
        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Collectors.toLinkedList, Stream<?>, LinkedList<?>>> read() default CollectorPipes.ToLinkedList.class;

        Class<? extends AnnotablePipe<?, Collection<?>, Stream<?>>> write() default CollectorPipes.CollectionToStream.class;

        String view() default Annotations.DEFAULT;
    }

    @Repeatable(Collectorss.toSets.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class})
    @interface toSet {
        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Collectors.toSet, Stream<?>, Set<?>>> read() default CollectorPipes.ToSet.class;

        Class<? extends AnnotablePipe<?, Collection<?>, Stream<?>>> write() default CollectorPipes.CollectionToStream.class;

        String view() default Annotations.DEFAULT;
    }

    @Repeatable(Collectorss.toTreeSets.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class})
    @interface toTreeSet {
        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Collectors.toTreeSet, Stream<?>, TreeSet<?>>> read() default CollectorPipes.ToTreeSet.class;

        Class<? extends AnnotablePipe<?, Collection<?>, Stream<?>>> write() default CollectorPipes.CollectionToStream.class;

        String view() default Annotations.DEFAULT;
    }

    @Repeatable(Collectorss.toMaps.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class})
    @interface toMap {

        /**
         * The name of the field (accessor) to be used as key.
         */
        String key();

        /**
         * The Class containing the field. Can actually be the type of data in the Stream or a superclass.
         */
        Class<?> clazz();

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Collectors.toMap, Stream<?>, Map<?, ?>>> read() default CollectorPipes.ToMap.class;

        Class<? extends AnnotablePipe<?, Map<?, ?>, Stream<?>>> write() default CollectorPipes.MapToStream.class;

        String view() default Annotations.DEFAULT;
    }

    @Repeatable(Collectorss.joinings.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class})
    @interface joining {

        /**
         * The separator to use.
         */
        String separator();

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Collectors.joining, Stream<String>, String>> read() default CollectorPipes.JoinStream.class;

        Class<? extends AnnotablePipe<Collectors.joining, String, Stream<String>>> write() default CollectorPipes.SplitStream.class;

        String view() default Annotations.DEFAULT;
    }
}
