/*
 *    Copyright 2022-2023  Alexandru Agache
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.github.agache41.ormpipes.pipes.base.parser;

import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.registry.PipeRegistry;

import static io.github.agache41.ormpipes.config.Constants.DEFAULT;


/**
 * <pre>
 * The type Parser.
 * </pre>
 *
 * @param <T>      the type parameter
 * @param <Input>  the type parameter
 * @param <Output> the type parameter
 */
public class Parser<T, Input, Output> {
    private final String view;
    private final Class<T> clazz;
    private final Class<Input> inputClass;
    private final AnnotablePipe<?, Input, Output> readPipe;
    private final AnnotablePipe<?, Output, ThrowingConsumer<Input>> writePipe;

    /**
     * <pre>
     * Instantiates a new Parser.
     * </pre>
     *
     * @param clazz      the clazz
     * @param inputClass the input class
     */
    public Parser(Class<T> clazz,
                  Class<Input> inputClass) {
        this(clazz, inputClass, DEFAULT);
    }

    /**
     * <pre>
     * Instantiates a new Parser.
     * </pre>
     *
     * @param clazz      the clazz
     * @param inputClass the input class
     * @param view       the view
     */
    public Parser(Class<T> clazz,
                  Class<Input> inputClass,
                  String view) {
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

    /**
     * <pre>
     * Read output.
     * </pre>
     *
     * @param input the input
     * @return the output
     * @throws Throwable the throwable
     */
    public Output read(Input input) throws Throwable {
        return this.readPipe.function()
                            .apply(input);
    }

    /**
     * <pre>
     * Write.
     * </pre>
     *
     * @param input  the input
     * @param output the output
     * @throws Throwable the throwable
     */
    public void write(Input input,
                      Output output) throws Throwable {
        this.writePipe.function()
                      .apply(output)
                      .accept(input);
    }
}
