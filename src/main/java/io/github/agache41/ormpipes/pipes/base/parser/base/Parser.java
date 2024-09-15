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

import static io.github.agache41.ormpipes.config.Constants.DEFAULT;


/**
 * <pre>
 * The type Parser.
 * </pre>
 *
 * @param <T>     the type parameter
 * @param <Input> the type parameter
 * @param <Data>  the type parameter
 */
public class Parser<T, Input, Data> extends BaseParser<T, Input, Input, Data> {
    /**
     * <pre>
     * Instantiates a new Parser.
     * </pre>
     *
     * @param clazz the clazz
     */
    public Parser(Class<T> clazz) {
        this(clazz, DEFAULT);
    }

    /**
     * <pre>
     * Instantiates a new Parser.
     * </pre>
     *
     * @param clazz the clazz
     * @param view  the view
     */
    public Parser(Class<T> clazz,
                  String view) {
        super(clazz, view);
    }
}
