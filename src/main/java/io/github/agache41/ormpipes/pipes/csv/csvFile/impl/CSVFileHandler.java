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

package io.github.agache41.ormpipes.pipes.csv.csvFile.impl;

import io.github.agache41.annotator.accessor.Accessor;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.pipes.base.othogonal.OrthogonalHandler;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.NamingMethod;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.PositionMethod;
import io.github.agache41.ormpipes.pipes.csv.csvField.CSVAccessor;
import io.github.agache41.ormpipes.pipes.csv.csvFile.CSVFile;

/**
 * <pre>
 * The type Csv file handler.
 * </pre>
 */
public class CSVFileHandler extends OrthogonalHandler<CSVFile, CSVAccessor, CSVEntry, String, ThrowingConsumer<Object>, Object, String> {
    /**
     * <pre>
     * Instantiates a new Csv file handler.
     * </pre>
     *
     * @param onClass the on class
     * @param cfg     the cfg
     */
    public CSVFileHandler(Class<?> onClass,
                          CSVFile cfg) {
        super(onClass, cfg, CSVAccessor.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CSVEntry newEntry(Accessor<?> accessor,
                                NamingMethod namingMethod,
                                PositionMethod positionMethod) {
        return new CSVEntry(accessor, namingMethod, positionMethod);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CSVEntry newEntry(String name,
                                int position) {
        return new CSVEntry(name, position);
    }
}
