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

import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * <pre>
 * The type Spread sheet file reader.
 * </pre>
 */
public class SpreadSheetFileReader implements AnnotablePipe<SpreadSheet.file, File, Workbook> {
    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(SpreadSheet.file cfg) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ThrowingFunction<File, Workbook> function() {
        return file -> {
            try (InputStream is = new FileInputStream(file)) {
                return WorkbookFactory.create(is);
            } catch (Throwable e) {
                logger.error(e);
                throw e;
            }
        };
    }
}
