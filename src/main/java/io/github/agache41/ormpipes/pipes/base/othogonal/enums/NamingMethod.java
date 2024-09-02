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

package io.github.agache41.ormpipes.pipes.base.othogonal.enums;

import java.util.function.BiFunction;

/**
 * <pre>
 * The enum Naming method.
 * </pre>
 */
public enum NamingMethod implements BiFunction<String, String, String> {
    /**
     * <pre>
     * The Accessor names.
     * </pre>
     */
    AccessorNames((accessorName, fieldName) -> {
        if (accessorName.isEmpty()) {
            throw new IllegalArgumentException(" No ***Accessor.name() found for " + fieldName + "!");
        }
        return accessorName;
    }),
    /**
     * <pre>
     * Java field names naming method.
     * </pre>
     */
    JavaFieldNames((accessorName, fieldName) -> fieldName);

    private final BiFunction<String, String, String> method;

    NamingMethod(BiFunction<String, String, String> method) {
        this.method = method;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String apply(String accessorName,
                        String fieldName) {
        return this.method.apply(accessorName, fieldName);
    }
}
