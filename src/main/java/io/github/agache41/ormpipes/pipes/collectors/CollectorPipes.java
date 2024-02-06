package io.github.agache41.ormpipes.pipes.collectors;

import io.github.agache41.annotator.accessor.Accessor;
import io.github.agache41.annotator.annotator.Annotator;
import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.typeString.TypeString;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * <pre>
 * The type Collector pipes.
 * </pre>
 */
public class CollectorPipes {

    /**
     * <pre>
     * The type To list.
     * </pre>
     */
    public static class ToList implements AnnotablePipe<Collectors.toList, Stream<?>, List<?>> {
        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return StrongType.of(List.class);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return StrongType.of(Stream.class);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(Collectors.toList cfg) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Stream<?>, List<?>> function() {
            return stream -> stream.collect(toList());
        }
    }

    /**
     * <pre>
     * The type To linked list.
     * </pre>
     */
    public static class ToLinkedList implements AnnotablePipe<Collectors.toLinkedList, Stream<?>, LinkedList<?>> {
        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return StrongType.of(LinkedList.class);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return StrongType.of(Stream.class);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(Collectors.toLinkedList cfg) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Stream<?>, LinkedList<?>> function() {
            return stream -> stream.collect(toCollection(LinkedList::new));
        }
    }

    /**
     * <pre>
     * The type To set.
     * </pre>
     */
    public static class ToSet implements AnnotablePipe<Collectors.toSet, Stream<?>, Set<?>> {
        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return StrongType.of(List.class);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return StrongType.of(Stream.class);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(Collectors.toSet cfg) {
        }


        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Stream<?>, Set<?>> function() {
            return stream -> stream.collect(toSet());
        }
    }

    /**
     * <pre>
     * The type To tree set.
     * </pre>
     */
    public static class ToTreeSet implements AnnotablePipe<Collectors.toTreeSet, Stream<?>, TreeSet<?>> {
        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return StrongType.of(TreeSet.class);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return StrongType.of(Stream.class);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(Collectors.toTreeSet cfg) {
        }


        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Stream<?>, TreeSet<?>> function() {
            return stream -> stream.collect(toCollection(TreeSet::new));
        }
    }

    /**
     * <pre>
     * The type Collection to stream.
     * </pre>
     */
    public static class CollectionToStream implements AnnotablePipe<Annotation, Collection<?>, Stream<?>> {
        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return StrongType.of(Stream.class);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return StrongType.of(Collection.class);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(Annotation cfg) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Collection<?>, Stream<?>> function() {
            return collection -> collection.stream();
        }
    }

    /**
     * <pre>
     * The type Map to stream.
     * </pre>
     */
    public static class MapToStream implements AnnotablePipe<Annotation, Map<?, ?>, Stream<?>> {
        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return StrongType.of(Stream.class);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return StrongType.of(Map.class);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(Annotation cfg) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Map<?, ?>, Stream<?>> function() {
            return map -> map.values()
                             .stream();
        }
    }

    /**
     * <pre>
     * The type To map.
     * </pre>
     */
    public static class ToMap implements AnnotablePipe<Collectors.toMap, Stream<?>, Map<?, ?>> {
        private Accessor<?> accessor;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return StrongType.of(Map.class);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return StrongType.of(Stream.class);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(Collectors.toMap cfg) {
            this.accessor = Annotator.of(cfg.clazz())
                                     .getAccessor(cfg.key());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Stream<?>, Map<?, ?>> function() {
            return stream -> stream.collect(toMap(this::getKey, Function.identity()));
        }

        private Object getKey(Object input) {
            return this.accessor.get(input);
        }
    }

    /**
     * <pre>
     * The type Join stream.
     * </pre>
     */
    public static class JoinStream implements AnnotablePipe<Collectors.joining, java.util.stream.Stream<String>, String> {
        private ThrowingFunction<java.util.stream.Stream<String>, String> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return StrongType.of(Stream.class)
                             .parameterizedWith(TypeString.strongType);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeString.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(Collectors.joining cfg) {
            final String separator = cfg.separator();
            this.function = stream -> stream.collect(java.util.stream.Collectors.joining(separator));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<java.util.stream.Stream<String>, String> function() {
            return this.function;
        }
    }

    /**
     * <pre>
     * The type Split stream.
     * </pre>
     */
    public static class SplitStream implements AnnotablePipe<Collectors.joining, String, Stream<String>> {
        private ThrowingFunction<String, Stream<String>> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeString.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return StrongType.of(Stream.class)
                             .parameterizedWith(TypeString.strongType);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(Collectors.joining cfg) {
            final String separator = cfg.separator();
            this.function = line -> Stream.of(line.split(separator, -1));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<String, Stream<String>> function() {
            return this.function;
        }
    }
}
