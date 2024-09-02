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

package io.github.agache41.ormpipes.pipes.spreadSheet.selector;

import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * <pre>
 * The type Read sheet selector.
 * </pre>
 */
public class ReadSheetSelector implements AnnotablePipe<SpreadSheet.select, Workbook, Sheet> {

    private String sheetName;
    private int sheetIndex;

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(SpreadSheet.select cfg) {
        this.sheetName = cfg.sheetName();
        this.sheetIndex = cfg.sheetIndex();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ThrowingFunction<Workbook, Sheet> function() {
        return this::readSheet;
    }

    /**
     * <pre>
     * Read sheet sheet.
     * </pre>
     *
     * @param workbook the workbook
     * @return the sheet
     */
    protected Sheet readSheet(Workbook workbook) {
        Sheet sheet;
        if (this.sheetName.isEmpty()) {
            sheet = workbook.getSheetAt(this.sheetIndex);
        } else {
            sheet = workbook.getSheet(this.sheetName);
        }
        if (sheet == null) {
            throw new RuntimeException(" No Sheet " + (this.sheetName.isEmpty() ? "at index " + this.sheetIndex : "named " + this.sheetName + " found!"));
        }
        return sheet;
    }
}

