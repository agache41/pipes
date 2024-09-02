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

package io.github.agache41.ormpipes.pipes.spreadSheet.font;

import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import io.github.agache41.ormpipes.pipes.spreadSheet.base.Styler;
import org.apache.poi.ss.usermodel.*;

/**
 * <pre>
 * The type Font styler.
 * </pre>
 */
public class FontStyler extends Styler<SpreadSheet.ContentFont> implements AnnotablePipe<SpreadSheet.ContentFont, ThrowingConsumer<Cell>, ThrowingConsumer<Cell>> {
    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(SpreadSheet.ContentFont cfg) {
        super.configure(cfg);
        this.configureStyle = (style, workbook) -> {
            Font font = workbook.createFont();
            font.setBold(cfg.bold());
            if (FontCharset.DEFAULT != cfg.charSet()) {
                font.setCharSet(cfg.charSet()
                                   .getValue());
            }
            if (IndexedColors.AUTOMATIC != cfg.color()) {
                font.setColor(cfg.color().index);
            }
            if (cfg.fontHeight() != -1) {
                font.setFontHeight(cfg.fontHeight());
            }
            if (cfg.fontHeightInPoints() != -1) {
                font.setFontHeightInPoints(cfg.fontHeightInPoints());
            }
            if (!cfg.fontName()
                    .isEmpty()) {
                font.setFontName(cfg.fontName());
            }
            font.setItalic(cfg.italic());
            font.setStrikeout(cfg.strikeout());
            if (cfg.typeOffset() != -1) {
                font.setTypeOffset(cfg.typeOffset());
            }
            if (cfg.underline() != FontUnderline.NONE) {
                font.setUnderline(cfg.underline()
                                     .getByteValue());
            }
            style.setFont(font);
            return style;
        };
    }
}
