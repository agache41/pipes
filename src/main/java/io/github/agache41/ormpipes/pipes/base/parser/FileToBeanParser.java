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

import io.github.agache41.ormpipes.pipes.base.parser.base.Parser;

import java.io.File;

/**
 * <pre>
 * The type File to bean parser.
 * </pre>
 *
 * @param <T> the type parameter
 */
public class FileToBeanParser<T> extends Parser<T, File, T> {

    /**
     * <pre>
     * Instantiates a new File to bean parser.
     * </pre>
     *
     * @param clazz the clazz
     */
    public FileToBeanParser(Class<T> clazz) {
        super(clazz, File.class);
    }

    /**
     * <pre>
     * Instantiates a new File to bean parser.
     * </pre>
     *
     * @param clazz the clazz
     * @param view  the view
     */
    public FileToBeanParser(Class<T> clazz,
                            String view) {
        super(clazz, File.class, view);
    }
}