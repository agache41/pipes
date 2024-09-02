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

package io.github.agache41.ormpipes.pipes.spreadSheet.file;

import java.util.function.Function;

/**
 * <pre>
 * The enum Type.
 * </pre>
 */
public enum Type {
    /**
     * <pre>
     * Xsl type.
     * </pre>
     */
    XSL(extension -> true), //
    /**
     * <pre>
     * Xslx type.
     * </pre>
     */
    XSLX(extension -> false), //
    /**
     * <pre>
     * The Auto.
     * </pre>
     */
    AUTO(extension -> {
        if ("xls".equalsIgnoreCase(extension)) {
            return true;
        } else if ("xlsx".equalsIgnoreCase(extension)) {
            return false;
        } else {
            throw new IllegalArgumentException(" Unknown file extension " + extension);
        }
    });

    private final Function<String, Boolean> type;

    Type(Function<String, Boolean> type) {
        this.type = type;
    }

    /**
     * <pre>
     * Gets type.
     * </pre>
     *
     * @param extension the extension
     * @return the type
     */
    public Boolean getType(String extension) {
        return this.type.apply(extension);
    }
}
