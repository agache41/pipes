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

import java.util.stream.Stream;

/**
 * <pre>
 * The type Stream parser.
 * </pre>
 *
 * @param <T>     the type parameter
 * @param <Input> the type parameter
 */
public abstract class StreamParser<T, Input> extends Parser<T, Input, Stream<T>> {
    /**
     * <pre>
     * Instantiates a new Stream parser.
     * </pre>
     *
     * @param clazz      the clazz
     * @param inputClass the input class
     */
    public StreamParser(Class<T> clazz,
                        Class<Input> inputClass) {
        super(clazz, inputClass);
    }

    /**
     * <pre>
     * Instantiates a new Stream parser.
     * </pre>
     *
     * @param clazz      the clazz
     * @param inputClass the input class
     * @param view       the view
     */
    public StreamParser(Class<T> clazz,
                        Class<Input> inputClass,
                        String view) {
        super(clazz, inputClass, view);
    }
}
