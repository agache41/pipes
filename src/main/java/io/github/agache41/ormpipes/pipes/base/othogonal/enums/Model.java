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

/**
 * <pre>
 * The enum Model.
 * </pre>
 */
public enum Model {
    /**
     * <pre>
     * Fixed model.
     * </pre>
     */
    Fixed(true, true),
    /**
     * <pre>
     * Variable positions model.
     * </pre>
     */
    VariablePositions(true, false),
    /**
     * <pre>
     * Variable names model.
     * </pre>
     */
    VariableNames(false, true);

    private final boolean validNames;
    private final boolean validPositions;

    Model(boolean validNames,
          boolean validPositions) {
        this.validNames = validNames;
        this.validPositions = validPositions;
    }

    /**
     * <pre>
     * Has valid names boolean.
     * </pre>
     *
     * @return the boolean
     */
    public boolean hasValidNames() {
        return this.validNames;
    }

    /**
     * <pre>
     * Has valid positions boolean.
     * </pre>
     *
     * @return the boolean
     */
    public boolean hasValidPositions() {
        return this.validPositions;
    }

    /**
     * <pre>
     * Verify.
     * </pre>
     *
     * @param validNames     the valid names
     * @param validPositions the valid positions
     */
    public void verify(boolean validNames,
                       boolean validPositions) {
        if (this.validNames && !validNames) {
            throw new RuntimeException(" No valid naming, please check your class!");
        }
        if (this.validPositions && !validPositions) {
            throw new RuntimeException(" No valid positioning, please check your class!");
        }
    }
}
