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

package io.github.agache41.ormpipes.pipes.spreadSheet.cellFormat;

import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import io.github.agache41.ormpipes.pipes.spreadSheet.base.Styler;
import org.apache.poi.ss.usermodel.Cell;

/**
 * <pre>
 * The type Format styler.
 * </pre>
 */
public class FormatStyler extends Styler<SpreadSheet.ContentFormat> implements AnnotablePipe<SpreadSheet.ContentFormat, ThrowingConsumer<Cell>, ThrowingConsumer<Cell>> {
    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(SpreadSheet.ContentFormat cfg) {
        super.configure(cfg);
        this.configureStyle = (style, workbook) -> {
            if (cfg.dataFormat() != -1) {
                style.setDataFormat(cfg.dataFormat());
            } else if (!cfg.value()
                           .isEmpty()) {
                style.setDataFormat(workbook.getCreationHelper()
                                            .createDataFormat()
                                            .getFormat(cfg.value()));
            }
            return style;
        };
    }
}
