package io.github.agache41.ormpipes.pipes.spreadSheet.sheet;

import io.github.agache41.ormpipes.functional.Helper;
import io.github.agache41.ormpipes.pipe.registry.Annotable;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import io.github.agache41.annotator.accessor.Accessor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public abstract class SheetBase implements Annotable<SpreadSheet.sheet>, Supplier<Object> {

    protected static final Logger logger = LogManager.getLogger(SpreadSheet.sheet.class);

    protected Class<?> onClass;
    protected String sheetName;
    protected SpreadSheet.sheet cfg;
    protected boolean useFirstLineAsHeader;
    protected boolean append;
    protected int skipFirstNLines;
    protected int offsetX;
    protected int offsetY;
    protected int defaultColumnWidth;
    protected short defaultRowHeight;
    protected int defaultRowHeightInPoints;

    protected boolean autoResizeAllColumns;
    private Supplier<?> constructor;

    @Override
    public Object get() {
        return this.constructor.get();
    }

    @Override
    public void configure(SpreadSheet.sheet cfg, Class<?> onClass, Field onField, Method onMethod, Accessor<?> onAccessor, String operation) {
        this.onClass = onClass;
        this.constructor = Helper.constructor(onClass);
        this.configure(cfg);
    }

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

    protected SheetHandler handler() {
        return new SheetHandler(this.onClass, this.cfg);
    }

    protected String[] getHeader(Row row) {
        return IntStream.range(this.offsetX, row.getLastCellNum())
                        .mapToObj(row::getCell)
                        .map(Cell::getStringCellValue)
                        .toArray(String[]::new);
    }
}


