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

package io.github.agache41.ormpipes.pipes.base.othogonal;

import io.github.agache41.ormpipes.pipes.base.othogonal.enums.Model;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.NamingMethod;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.PositionMethod;

/**
 * <pre>
 * The interface Orthogonal file.
 * </pre>
 */
public @interface OrthogonalFile {
    /**
     * <pre>
     * Header string [ ].
     * </pre>
     *
     * @return the string [ ]
     */
    String[] header() default {};

    /**
     * <pre>
     * Use first line as header boolean.
     * </pre>
     *
     * @return the boolean
     */
    boolean useFirstLineAsHeader() default true;

    /**
     * <pre>
     * Skip first n lines int.
     * </pre>
     *
     * @return the int
     */
    int skipFirstNLines() default 0;

    /**
     * <pre>
     * Naming method naming method.
     * </pre>
     *
     * @return the naming method
     */
    NamingMethod namingMethod() default NamingMethod.AccessorNames;

    /**
     * <pre>
     * Position method position method.
     * </pre>
     *
     * @return the position method
     */
    PositionMethod positionMethod() default PositionMethod.CSVFields;

    /**
     * <pre>
     * Model model.
     * </pre>
     *
     * @return the model
     */
    Model model() default Model.VariablePositions;
}
