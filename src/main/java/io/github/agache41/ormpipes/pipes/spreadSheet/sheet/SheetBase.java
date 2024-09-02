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

import io.github.agache41.annotator.accessor.Accessor;
import io.github.agache41.ormpipes.functional.Helper;
import io.github.agache41.ormpipes.pipe.registry.Annotable;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.jboss.logging.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * <pre>
 * The type Sheet base.
 * </pre>
 */
public abstract class SheetBase implements Annotable<SpreadSheet.sheet>, Supplier<Object> {

    /**
     * <pre>
     * The constant logger.
     * </pre>
     */
    protected static final Logger logger = Logger.getLogger(SpreadSheet.sheet.class);

    /**
     * <pre>
     * The On class.
     * </pre>
     */
    protected Class<?> onClass;
    /**
     * <pre>
     * The Sheet name.
     * </pre>
     */
    protected String sheetName;
    /**
     * <pre>
     * The Cfg.
     * </pre>
     */
    protected SpreadSheet.sheet cfg;
    /**
     * <pre>
     * The Use first line as header.
     * </pre>
     */
    protected boolean useFirstLineAsHeader;
    /**
     * <pre>
     * The Append.
     * </pre>
     */
    protected boolean append;
    /**
     * <pre>
     * The Skip first n lines.
     * </pre>
     */
    protected int skipFirstNLines;
    /**
     * <pre>
     * The Offset x.
     * </pre>
     */
    protected int offsetX;
    /**
     * <pre>
     * The Offset y.
     * </pre>
     */
    protected int offsetY;
    /**
     * <pre>
     * The Default column width.
     * </pre>
     */
    protected int defaultColumnWidth;
    /**
     * <pre>
     * The Default row height.
     * </pre>
     */
    protected short defaultRowHeight;
    /**
     * <pre>
     * The Default row height in points.
     * </pre>
     */
    protected int defaultRowHeightInPoints;

    /**
     * <pre>
     * The Auto resize all columns.
     * </pre>
     */
    protected boolean autoResizeAllColumns;
    private Supplier<?> constructor;

    /**
     * {@inheritDoc}
     */
    @Override
    public Object get() {
        return this.constructor.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(SpreadSheet.sheet cfg,
                          Class<?> onClass,
                          Field onField,
                          Method onMethod,
                          Accessor<?> onAccessor,
                          String operation) {
        this.onClass = onClass;
        this.constructor = Helper.constructor(onClass);
        this.configure(cfg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(SpreadSheet.sheet cfg) {
        this.cfg = cfg;
        this.useFirstLineAsHeader = cfg.useFirstLineAsHeader();
        this.append = cfg.append();
        this.skipFirstNLines = cfg.skipFirstNLines();
        this.offsetX = cfg.offsetX();
        this.offsetY = cfg.offsetY();
        this.defaultColumnWidth = cfg.defaultColumnWidth();
        this.defaultRowHeight = cfg.defaultRowHeight();
        this.defaultRowHeightInPoints = cfg.defaultRowHeightInPoints();
        this.autoResizeAllColumns = cfg.autoResizeAllColumns();
    }

    /**
     * <pre>
     * Handler sheet handler.
     * </pre>
     *
     * @return the sheet handler
     */
    protected SheetHandler handler() {
        return new SheetHandler(this.onClass, this.cfg);
    }

    /**
     * <pre>
     * Get header string [ ].
     * </pre>
     *
     * @param row the row
     * @return the string [ ]
     */
    protected String[] getHeader(Row row) {
        return IntStream.range(this.offsetX, row.getLastCellNum())
                        .mapToObj(row::getCell)
                        .map(Cell::getStringCellValue)
                        .toArray(String[]::new);
    }
}


