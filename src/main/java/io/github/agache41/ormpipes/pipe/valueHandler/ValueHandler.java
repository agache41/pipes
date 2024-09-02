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

package io.github.agache41.ormpipes.pipe.valueHandler;

/**
 * <pre>
 * The interface Value handler.
 * </pre>
 */
public interface ValueHandler {

    /**
     * <pre>
     * Handle a value output for a specific type and input value
     * </pre>
     *
     * @param <Output>    the type parameter
     * @param outputClass the output class
     * @param inputValue  the input value
     * @return the output
     */
    <Output> Output handleValue(Class<Output> outputClass,
                                Object inputValue);
}
