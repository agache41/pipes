package com.orm.pipes.base.parser;

import com.orm.functional.ThrowingConsumer;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipe.registry.PipeRegistry;

import static com.orm.annotations.Annotations.DEFAULT;


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
