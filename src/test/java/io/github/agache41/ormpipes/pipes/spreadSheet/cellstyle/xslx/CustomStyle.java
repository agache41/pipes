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

package io.github.agache41.ormpipes.pipes.spreadSheet.cellstyle.xslx;

import org.apache.poi.ss.usermodel.*;

import java.util.function.BiFunction;

public class CustomStyle implements BiFunction<CellStyle, Workbook, CellStyle> {
    /**
* {@inheritDoc}
*/
@Override
    public CellStyle apply(CellStyle cellStyle, Workbook workbook) {
        cellStyle.setFillForegroundColor(IndexedColors.LAVENDER.index);
        cellStyle.setFillPattern(FillPatternType.BRICKS);
        cellStyle.setFillBackgroundColor(IndexedColors.LAVENDER.index);
        Font font = workbook.createFont();
        font.setFontName("Courier New");
        font.setColor(IndexedColors.TURQUOISE1.index);
        return cellStyle;
    }
}
