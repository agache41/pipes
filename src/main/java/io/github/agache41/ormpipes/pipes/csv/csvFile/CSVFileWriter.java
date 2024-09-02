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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * <pre>
 * The type Csv file writer.
 * </pre>
 */
public class CSVFileWriter extends CSVFileBase implements AnnotablePipe<CSVFile, Stream<?>, Stream<String[]>> {

    /**
     * {@inheritDoc}
     */
    @Override
    public StrongType getInputType() {
        return StrongType.of(Stream.class)
                         .parameterizedWith("?");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StrongType getOutputType() {
        return StrongType.of(Stream.class)
                         .parameterizedWith(String[].class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ThrowingFunction<Stream<?>, Stream<String[]>> function() {
        return input -> {
            CSVFileHandler handler = this.handler();
            handler.validate();
            if (handler.useFirstLineAsHeader) {
                if (handler.validHeader) {
                    List<String[]> headerList = new ArrayList<>();
                    headerList.add(handler.header);
                    return Stream.concat(headerList.stream(),
                                         input.map(ThrowingFunction.wrap(line -> this.doLine(handler, line))));
                } else {
                    throw new RuntimeException(" Header is not available for writing the first line !" + handler);
                }
            }
            return input.map(ThrowingFunction.wrap(line -> this.doLine(handler, line)));
        };
    }

    private String[] doLine(CSVFileHandler handler,
                            Object input) throws Throwable {
        String[] result = new String[handler.positionIndex.length];
        for (int index = 0; index < handler.positionIndex.length; index++) {
            result[handler.positionIndex[index]] = handler.writePipes[index].apply(input);
        }
        return result;
    }
}
