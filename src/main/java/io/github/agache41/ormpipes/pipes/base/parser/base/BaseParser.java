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

package io.github.agache41.ormpipes.pipes.base.parser.base;

import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.registry.PipeRegistry;

import static io.github.agache41.ormpipes.config.Constants.DEFAULT;


/**
 * <pre>
 * The type Base parser.
 * </pre>
 *
 * @param <T>      the type parameter
 * @param <Input>  the type parameter
 * @param <Output> the type parameter
 * @param <Data>   the type parameter
 */
public class BaseParser<T, Input, Output, Data> {
    private final String view;
    private final Class<T> clazz;
    private final AnnotablePipe<?, Input, Data> readPipe;
    private final AnnotablePipe<?, Data, ThrowingConsumer<Output>> writePipe;

    /**
     * <pre>
     * Instantiates a new Base parser.
     * </pre>
     *
     * @param clazz the clazz
     */
    public BaseParser(Class<T> clazz) {
        this(clazz, DEFAULT);
    }

    /**
     * <pre>
     * Instantiates a new Base parser.
     * </pre>
     *
     * @param clazz the clazz
     * @param view  the view
     */
    public BaseParser(Class<T> clazz,
                      String view) {
        this.clazz = clazz;
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
     * Read data.
     * </pre>
     *
     * @param input the input
     * @return the data
     * @throws Throwable the throwable
     */
    public Data read(Input input) throws Throwable {
        return this.readPipe.function()
                            .apply(input);
    }

    /**
     * <pre>
     * Write.
     * </pre>
     *
     * @param output the output
     * @param data   the data
     * @throws Throwable the throwable
     */
    public void write(Output output,
                      Data data) throws Throwable {
        this.writePipe.function()
                      .apply(data)
                      .accept(output);
    }
}
