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

package io.github.agache41.ormpipes.pipes.spreadSheet.xlsx;

import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.OutputStream;

/**
 * <pre>
 * The type Xlsx writer.
 * </pre>
 */
public class XLSXWriter implements AnnotablePipe<SpreadSheet.xlsx, ThrowingConsumer<XSSFWorkbook>, ThrowingConsumer<OutputStream>> {
    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(SpreadSheet.xlsx cfg) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ThrowingFunction<ThrowingConsumer<XSSFWorkbook>, ThrowingConsumer<OutputStream>> function() {
        return workbookThrowingConsumer -> outputStream -> {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
            workbookThrowingConsumer.accept(xssfWorkbook);
            xssfWorkbook.write(outputStream);
            xssfWorkbook.close();
            outputStream.close();
        };
    }
}
