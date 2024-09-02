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

package io.github.agache41.ormpipes.pipes.spreadSheet.sheet;

import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static io.github.agache41.ormpipes.functional.ThrowingFunction.wrap;

/**
 * <pre>
 * The type Sheet reader.
 * </pre>
 */
public class SheetReader extends SheetBase implements AnnotablePipe<SpreadSheet.sheet, Sheet, Stream<?>> {
    /**
     * {@inheritDoc}
     */
    @Override
    public StrongType getInputType() {
        return StrongType.of(Sheet.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StrongType getOutputType() {
        return StrongType.of(Stream.class)
                         .parameterizedWith(this.onClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ThrowingFunction<Sheet, Stream<?>> function() {
        return sheet -> {
            final SheetHandler handler = this.handler();
            this.doSheetSettings(handler);
            return StreamSupport.stream(sheet.spliterator(), false)
                                .skip(this.offsetY)
                                .skip(this.skipFirstNLines)
                                .map(wrap(row -> this.processLine(handler, row)))
                                .filter(Objects::nonNull)
                                .sequential();
        };
    }

    private Object processLine(SheetHandler handler,
                               Row row) throws Throwable {
        handler.lineNumber++;
        if (handler.useFirstLineAsHeader) {
            handler.setHeader(this.getHeader(row));
            handler.useFirstLineAsHeader = false;
            this.doSheetSettings(handler);
            return null;
        }
        if (!this.cfg.disableLineValidation()) {
            handler.validateLine(row.getPhysicalNumberOfCells());
        }
        Object result = this.get();
        for (int index = 0; index < handler.positionIndex.length; index++) {
            handler.readPipes[index]
                    .apply(row.getCell(handler.positionIndex[index]))
                    .accept(result);
        }
        return result;
    }

    private void doSheetSettings(SheetHandler handler) {
        if (this.offsetY < 0) {
            this.offsetY = 0;
        }
        if (this.offsetX > 0) {
            for (int index = 0; index < handler.positionIndex.length; index++)
                handler.positionIndex[index] += this.offsetX;
        } else {
            this.offsetX = 0;
        }
    }
}

