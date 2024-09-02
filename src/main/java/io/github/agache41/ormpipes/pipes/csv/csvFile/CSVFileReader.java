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

package io.github.agache41.ormpipes.pipes.csv.csvFile;

import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.csv.csvFile.impl.CSVFileBase;
import io.github.agache41.ormpipes.pipes.csv.csvFile.impl.CSVFileHandler;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * <pre>
 * The type Csv file reader.
 * </pre>
 */
public class CSVFileReader extends CSVFileBase implements AnnotablePipe<CSVFile, Stream<String[]>, Stream<?>> {


    /**
     * {@inheritDoc}
     */
    @Override
    public StrongType getInputType() {
        return StrongType.of(Stream.class)
                         .parameterizedWith(String[].class);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StrongType getOutputType() {
        return StrongType.of(Stream.class)
                         .parameterizedWith("?");
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public ThrowingFunction<Stream<String[]>, Stream<?>> function() {
        return inputStream -> {
            CSVFileHandler handler = this.handler();
            return inputStream.skip(this.skipFirstNLines)
                              .map(ThrowingFunction.wrap(line -> this.doLine(handler, line)))
                              .filter(Objects::nonNull)
                              .sequential();
        };
    }

    private Object doLine(CSVFileHandler handler,
                          String[] line) throws Throwable {
        handler.lineNumber++;
        if (handler.useFirstLineAsHeader) {
            handler.setHeader(line);
            handler.useFirstLineAsHeader = false;
            return null;
        }
        handler.validateLine(line.length);
        Object result = this.get();
        for (int index = 0; index < handler.positionIndex.length; index++) {
            handler.readPipes[index]
                    .apply(line[handler.positionIndex[index]])
                    .accept(result);
        }
        return result;
    }
}
