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
 * The enum Position method.
 * </pre>
 */
public enum PositionMethod implements BiFunction<Integer, Integer, Integer> {
    /**
     * <pre>
     * Csv fields position method.
     * The positions of the columns will be taken from the CSVAccessor Annotation
     * </pre>
     */
    CSVFields((csvColumnPosition, fieldPosition) -> csvColumnPosition),
    /**
     * <pre>
     * Fields position method.
     * The positions of the columns will be taken from the Position Annotation
     * </pre>
     */
    Fields((csvColumnPosition, fieldPosition) -> fieldPosition);

    private final BiFunction<Integer, Integer, Integer> method;

    PositionMethod(BiFunction<Integer, Integer, Integer> method) {
        this.method = method;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Integer apply(Integer csvFieldPosition,
                         Integer fieldPosition) {
        return this.method.apply(csvFieldPosition, fieldPosition);
    }
}
