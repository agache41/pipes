package io.github.agache41.ormpipes.pipes.base.parser;

import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.registry.PipeRegistry;

import static io.github.agache41.ormpipes.config.Annotations.DEFAULT;


public class Parser<T, Input, Output> {
    private final String view;
    private final Class<T> clazz;
    private final Class<Input> inputClass;
    private final AnnotablePipe<?, Input, Output> readPipe;
    private final AnnotablePipe<?, Output, ThrowingConsumer<Input>> writePipe;

    public Parser(Class<T> clazz, Class<Input> inputClass) {
        this(clazz, inputClass, DEFAULT);
    }

    public Parser(Class<T> clazz, Class<Input> inputClass, String view) {
        this.clazz = clazz;
        this.inputClass = inputClass;
        this.view = view;
        this.readPipe = PipeRegistry.buildPipeFrom(clazz,
                view,
                "read",
                false);
        this.writePipe = PipeRegistry.buildPipeFrom(clazz,
                view,
                "write",
                true);
    }

    public Output read(Input input) throws Throwable {
        return this.readPipe.function()
                            .apply(input);
    }

    public void write(Input input, Output output) throws Throwable {
        this.writePipe.function()
                      .apply(output)
                      .accept(input);
    }
}
