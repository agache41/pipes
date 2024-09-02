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

package io.github.agache41.ormpipes.pipes.spreadSheet.base;

import io.github.agache41.annotator.annotator.Annotator;
import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import java.lang.annotation.Annotation;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * <pre>
 * The type Styler.
 * </pre>
 *
 * @param <A> the type parameter
 */
public abstract class Styler<A extends Annotation> implements AnnotablePipe<A, ThrowingConsumer<Cell>, ThrowingConsumer<Cell>> {
    private final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
    /**
     * <pre>
     * The Configure style.
     * </pre>
     */
    protected BiFunction<CellStyle, Workbook, CellStyle> configureStyle;
    private Function<AtomicBoolean, Boolean> toggle;
    private CellStyle cellStyle;

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(A cfg) {
        Line useOn = Annotator.of(cfg)
                              .getAccessor("useOn")
                              .getAs(cfg, Line.class);
        this.toggle = useOn.getToggle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StrongType getOutputType() {
        return StrongType.of(ThrowingConsumer.class)
                         .parameterizedWith(Cell.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StrongType getInputType() {
        return StrongType.of(ThrowingConsumer.class)
                         .parameterizedWith(Cell.class);
    }

    /**
     * <pre>
     * Create style cell style.
     * </pre>
     *
     * @param cell the cell
     * @return the cell style
     */
    protected CellStyle createStyle(Cell cell) {
        // create the style
        final CellStyle style = cell.getSheet()
                                    .getWorkbook()
                                    .createCellStyle();
        // copy any styles already set
        final CellStyle initialCellStyle = cell.getCellStyle();
        if (initialCellStyle != null) {
            style.cloneStyleFrom(initialCellStyle);
        }
        return style;
    }

    /**
     * <pre>
     * Gets style.
     * </pre>
     *
     * @param cell the cell
     * @return the style
     */
    protected CellStyle getStyle(Cell cell) {
        //singleton
        if (this.cellStyle == null) {
            this.cellStyle = this.configureStyle.apply(this.createStyle(cell), cell.getSheet()
                                                                                   .getWorkbook());
        }
        return this.cellStyle;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public ThrowingFunction<ThrowingConsumer<Cell>, ThrowingConsumer<Cell>> function() {
        return cellThrowingConsumer -> cell -> {
            //set the style
            if (this.toggle.apply(this.atomicBoolean)) {
                cell.setCellStyle(this.getStyle(cell));
            }
            cellThrowingConsumer.accept(cell);
        };
    }
}
