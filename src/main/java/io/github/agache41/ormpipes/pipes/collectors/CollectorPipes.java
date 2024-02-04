package io.github.agache41.ormpipes.pipes.collectors;

import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.typeString.TypeString;
import io.github.agache41.annotator.accessor.Accessor;
import io.github.agache41.annotator.annotator.Annotator;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class CollectorPipes {

    public static class ToList implements AnnotablePipe<Collectors.toList, Stream<?>, List<?>> {
        @Override
        public StrongType getOutputType() {
            return StrongType.of(List.class);
        }

        @Override
        public StrongType getInputType() {
            return StrongType.of(Stream.class);
        }

        @Override
        public void configure(Collectors.toList cfg) {
        }

        @Override
        public ThrowingFunction<Stream<?>, List<?>> function() {
            return stream -> stream.collect(toList());
        }
    }

    public static class ToLinkedList implements AnnotablePipe<Collectors.toLinkedList, Stream<?>, LinkedList<?>> {
        @Override
        public StrongType getOutputType() {
            return StrongType.of(LinkedList.class);
        }

        @Override
        public StrongType getInputType() {
            return StrongType.of(Stream.class);
        }

        @Override
        public void configure(Collectors.toLinkedList cfg) {
        }

        @Override
        public ThrowingFunction<Stream<?>, LinkedList<?>> function() {
            return stream -> stream.collect(toCollection(LinkedList::new));
        }
    }

    public static class ToSet implements AnnotablePipe<Collectors.toSet, Stream<?>, Set<?>> {
        @Override
        public StrongType getOutputType() {
            return StrongType.of(List.class);
        }

        @Override
        public StrongType getInputType() {
            return StrongType.of(Stream.class);
        }

        @Override
        public void configure(Collectors.toSet cfg) {
        }


        @Override
        public ThrowingFunction<Stream<?>, Set<?>> function() {
            return stream -> stream.collect(toSet());
        }
    }

    public static class ToTreeSet implements AnnotablePipe<Collectors.toTreeSet, Stream<?>, TreeSet<?>> {
        @Override
        public StrongType getOutputType() {
            return StrongType.of(TreeSet.class);
        }

        @Override
        public StrongType getInputType() {
            return StrongType.of(Stream.class);
        }

        @Override
        public void configure(Collectors.toTreeSet cfg) {
        }


        @Override
        public ThrowingFunction<Stream<?>, TreeSet<?>> function() {
            return stream -> stream.collect(toCollection(TreeSet::new));
        }
    }

    public static class CollectionToStream implements AnnotablePipe<Annotation, Collection<?>, Stream<?>> {
        @Override
        public StrongType getOutputType() {
            return StrongType.of(Stream.class);
        }

        @Override
        public StrongType getInputType() {
            return StrongType.of(Collection.class);
        }

        @Override
        public void configure(Annotation cfg) {
        }

        @Override
        public ThrowingFunction<Collection<?>, Stream<?>> function() {
            return collection -> collection.stream();
        }
    }

    public static class MapToStream implements AnnotablePipe<Annotation, Map<?, ?>, Stream<?>> {
        @Override
        public StrongType getOutputType() {
            return StrongType.of(Stream.class);
        }

        @Override
        public StrongType getInputType() {
            return StrongType.of(Map.class);
        }

        @Override
        public void configure(Annotation cfg) {
        }

        @Override
        public ThrowingFunction<Map<?, ?>, Stream<?>> function() {
            return map -> map.values()
                             .stream();
        }
    }

    public static class ToMap implements AnnotablePipe<Collectors.toMap, Stream<?>, Map<?, ?>> {
        private Accessor<?> accessor;

        @Override
        public StrongType getOutputType() {
            return StrongType.of(Map.class);
        }

        @Override
        public StrongType getInputType() {
            return StrongType.of(Stream.class);
        }

        @Override
        public void configure(Collectors.toMap cfg) {
            this.accessor = Annotator.of(cfg.clazz())
                                     .getAccessor(cfg.key());
        }

        @Override
        public ThrowingFunction<Stream<?>, Map<?, ?>> function() {
            return stream -> stream.collect(toMap(this::getKey, Function.identity()));
        }

        private Object getKey(Object input) {
            return this.accessor.get(input);
        }
    }

    public static class JoinStream implements AnnotablePipe<Collectors.joining, java.util.stream.Stream<String>, String> {
        private ThrowingFunction<java.util.stream.Stream<String>, String> function;

        @Override
        public StrongType getInputType() {
            return StrongType.of(Stream.class)
                             .parameterizedWith(TypeString.strongType);
        }

        @Override
        public StrongType getOutputType() {
            return TypeString.strongType;
        }

        @Override
        public void configure(Collectors.joining cfg) {
            final String separator = cfg.separator();
            this.function = stream -> stream.collect(java.util.stream.Collectors.joining(separator));
        }

        @Override
        public ThrowingFunction<java.util.stream.Stream<String>, String> function() {
            return this.function;
        }
    }

    public static class SplitStream implements AnnotablePipe<Collectors.joining, String, Stream<String>> {
        private ThrowingFunction<String, Stream<String>> function;

        @Override
        public StrongType getInputType() {
            return TypeString.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return StrongType.of(Stream.class)
                             .parameterizedWith(TypeString.strongType);
        }

        @Override
        public void configure(Collectors.joining cfg) {
            final String separator = cfg.separator();
            this.function = line -> Stream.of(line.split(separator, -1));
        }

        @Override
        public ThrowingFunction<String, Stream<String>> function() {
            return this.function;
        }
    }
}
