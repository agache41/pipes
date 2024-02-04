package io.github.agache41.ormpipes.pipes.field;

import io.github.agache41.ormpipes.functional.Helper;
import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.registry.PipeRegistry;
import io.github.agache41.annotator.accessor.Accessor;
import io.github.agache41.annotator.annotator.Annotator;
import io.github.agache41.annotator.matcher.HaveAnnotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class FieldPipes {
    public static class ReadForEachAnnotatedWith implements AnnotablePipe<Field.forEachAnnotatedWith, Object, Object> {

        protected Class<?> onClass;
        private String view;
        private Supplier<?> constructor;
        private LinkedList<AnnotablePipe<?, Object, ThrowingConsumer<Object>>> readPipes;

        private Class<? extends Annotation> annotatedWith;

        @Override
        public StrongType getInputType() {
            return StrongType.of("?");
        }

        @Override
        public StrongType getOutputType() {
            return StrongType.of(this.onClass);
        }

        @Override
        public void configure(Field.forEachAnnotatedWith cfg, Class<?> onClass, java.lang.reflect.Field onField, Method onMethod, Accessor<?> accessor, String operation) {
            this.onClass = onClass;
            this.constructor = Helper.constructor(onClass);
            this.configure(cfg);
        }

        @Override
        public void configure(Field.forEachAnnotatedWith cfg) {
            this.view = cfg.view();
            this.annotatedWith = cfg.value();
            this.readPipes = Annotator.of(this.onClass)
                                      .getAccessorsThat(HaveAnnotation.ofType(this.annotatedWith))
                                      .map(accessor -> {
                                          final AnnotablePipe<?, Object, ThrowingConsumer<Object>> readPipe = PipeRegistry.buildPipeFrom(accessor, this.view, "read", false);
                                          return readPipe;
                                      })
                                      .collect(Collectors.toCollection(LinkedList::new));
            if (this.readPipes.isEmpty())
                throw new IllegalArgumentException(" No fields annotated with " + this.annotatedWith.getSimpleName() + " in class " + this.onClass.getSimpleName());
            else logger.debug("Processing pipes from " + this.readPipes.size() + " fields");
        }

        @Override
        public ThrowingFunction<Object, Object> function() {
            return input -> {
                Object rootObject = this.constructor.get();
                for (AnnotablePipe<?, Object, ThrowingConsumer<Object>> readPipe : this.readPipes)
                    readPipe.function()
                            .apply(input)
                            .accept(rootObject);
                return rootObject;
            };
        }
    }

    public static class WriteForEachAnnotatedWith implements AnnotablePipe<Field.forEachAnnotatedWith, Object, ThrowingConsumer<?>> {

        private String view;
        private Class<?> onClass;

        private LinkedList<AnnotablePipe<?, Object, ThrowingConsumer<Object>>> writePipes;
        private Class<? extends Annotation> annotatedWith;

        @Override
        public StrongType getInputType() {
            return StrongType.of(this.onClass);
        }

        @Override
        public StrongType getOutputType() {
            return StrongType.of(ThrowingConsumer.class)
                             .parameterizedWith("?");
        }

        @Override
        public void configure(Field.forEachAnnotatedWith cfg, Class<?> onClass, java.lang.reflect.Field onField, Method onMethod, Accessor<?> accessor, String operation) {
            this.onClass = onClass;
            this.configure(cfg);
        }

        @Override
        public void configure(Field.forEachAnnotatedWith cfg) {
            this.view = cfg.view();
            this.annotatedWith = cfg.value();
            this.writePipes = Annotator.of(this.onClass)
                                       .getAccessorsThat(HaveAnnotation.ofType(this.annotatedWith))
                                       .map(accessor -> {
                                           final AnnotablePipe<?, Object, ThrowingConsumer<Object>> writePipe = PipeRegistry.buildPipeFrom(accessor, this.view, "write", true);
                                           return writePipe;
                                       })
                                       .collect(Collectors.toCollection(LinkedList::new));
            if (this.writePipes.isEmpty())
                throw new IllegalArgumentException(" No fields annotated with " + this.annotatedWith.getSimpleName() + " in class " + this.onClass.getSimpleName());
            else logger.debug("Processing pipes from " + this.writePipes.size() + " fields");
        }


        @Override
        public ThrowingFunction<Object, ThrowingConsumer<?>> function() {
            return rootObject -> input -> {
                for (AnnotablePipe<?, Object, ThrowingConsumer<Object>> writePipe : this.writePipes)
                    writePipe.function()
                             .apply(rootObject)
                             .accept(input);
            };
        }
    }
}
