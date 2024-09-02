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

package io.github.agache41.ormpipes.pipes.spreadSheet.cellStyle;

import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import io.github.agache41.ormpipes.pipes.spreadSheet.base.Styler;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * <pre>
 * The type Style styler.
 * </pre>
 */
public class StyleStyler extends Styler<SpreadSheet.ContentStyle> implements AnnotablePipe<SpreadSheet.ContentStyle, ThrowingConsumer<Cell>, ThrowingConsumer<Cell>> {
    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(SpreadSheet.ContentStyle cfg) {
        super.configure(cfg);
        this.configureStyle = (style, workbook) -> {
            // if (cfg.alignment() != HorizontalAlignment.DISTRIBUTED)
            style.setAlignment(cfg.alignment());
            if (cfg.borderBottom() != BorderStyle.NONE) {
                style.setBorderBottom(cfg.borderBottom());
            }
            if (cfg.borderLeft() != BorderStyle.NONE) {
                style.setBorderLeft(cfg.borderLeft());
            }
            if (cfg.borderRight() != BorderStyle.NONE) {
                style.setBorderRight(cfg.borderRight());
            }
            if (cfg.borderTop() != BorderStyle.NONE) {
                style.setBorderTop(cfg.borderTop());
            }
            if (cfg.bottomBorderColor() != IndexedColors.AUTOMATIC) {
                style.setBottomBorderColor(cfg.bottomBorderColor().index);
            }

            //void setDataFormat ( short fmt)
            //set the data format (must be a valid format).

            if (cfg.fillForegroundColor() != IndexedColors.AUTOMATIC) {
                style.setFillForegroundColor(cfg.fillForegroundColor().index);
            }
            if (cfg.fillBackgroundColor() != IndexedColors.AUTOMATIC) {
                style.setFillBackgroundColor(cfg.fillBackgroundColor().index);
            }
            if (cfg.fillPattern() != FillPatternType.NO_FILL) {
                style.setFillPattern(cfg.fillPattern());
            }
            style.setHidden(cfg.hidden());
            if (cfg.indention() != -1) {
                style.setIndention(cfg.indention());
            }
            if (cfg.leftBorderColor() != IndexedColors.AUTOMATIC) {
                style.setLeftBorderColor(cfg.leftBorderColor().index);
            }
            style.setLocked(cfg.locked());
            style.setQuotePrefixed(cfg.quotePrefixed());
            if (cfg.rightBorderColor() != IndexedColors.AUTOMATIC) {
                style.setRightBorderColor(cfg.rightBorderColor().index);
            }
            if (cfg.rotation() != -1) {
                style.setRotation(cfg.rotation());
            }
            style.setShrinkToFit(cfg.shrinkToFit());
            if (cfg.topBorderColor() != IndexedColors.AUTOMATIC) {
                style.setTopBorderColor(cfg.topBorderColor().index);
            }
            // if (cfg.verticalAlignment() != VerticalAlignment.DISTRIBUTED)
            style.setVerticalAlignment(cfg.verticalAlignment());
            style.setWrapText(cfg.wrapText());
            return style;
        };
    }
}
