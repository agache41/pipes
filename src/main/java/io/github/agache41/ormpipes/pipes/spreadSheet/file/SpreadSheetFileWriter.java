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

import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

/**
 * <pre>
 * The type Spread sheet file writer.
 * </pre>
 */
public class SpreadSheetFileWriter implements AnnotablePipe<SpreadSheet.file, ThrowingConsumer<Workbook>, ThrowingConsumer<File>> {
    private boolean template;
    private Type type;

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(SpreadSheet.file cfg) {
        this.template = cfg.template();
        this.type = cfg.type();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ThrowingFunction<ThrowingConsumer<Workbook>, ThrowingConsumer<File>> function() {
        return workbookThrowingConsumer -> file -> {
            final String extension = FilenameUtils.getExtension(file.getName());
            boolean useHssf = this.type.getType(extension);
            Workbook workbook;
            if (this.template) {
                try (InputStream is = new FileInputStream(file)) {
                    workbook = WorkbookFactory.create(is);
                }
            } else {
                workbook = useHssf ? new HSSFWorkbook() : new XSSFWorkbook();
            }
            workbookThrowingConsumer.accept(workbook);
            try (OutputStream outputStream = new FileOutputStream(file)) {
                workbook.write(outputStream);
            } finally {
                workbook.close();
            }
        };
    }
}
