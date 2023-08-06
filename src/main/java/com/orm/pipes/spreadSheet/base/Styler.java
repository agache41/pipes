package com.orm.pipes.spreadSheet.base;

import com.orm.functional.StrongType;
import com.orm.functional.ThrowingConsumer;
import com.orm.functional.ThrowingFunction;
import com.orm.pipe.AnnotablePipe;
import com.orm.reflection.annotator.Annotator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import java.lang.annotation.Annotation;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class Styler<A extends Annotation> implements AnnotablePipe<A, ThrowingConsumer<Cell>, ThrowingConsumer<Cell>> {
    private final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
    protected BiFunction<CellStyle, Workbook, CellStyle> configureStyle;
    private Function<AtomicBoolean, Boolean> toggle;
    private CellStyle cellStyle;

    @Override
    public void configure(A cfg) {
        Line useOn = Annotator.of(cfg)
                              .getAccessor("useOn")
                              .getAs(cfg, Line.class);
        this.toggle = useOn.getToggle();
    }

    @Override
    public StrongType getOutputType() {
        return StrongType.of(ThrowingConsumer.class)
                         .parameterizedWith(Cell.class);
    }

    @Override
    public StrongType getInputType() {
        return StrongType.of(ThrowingConsumer.class)
                         .parameterizedWith(Cell.class);
    }

    protected CellStyle createStyle(Cell cell) {
        // create the style
        final CellStyle style = cell.getSheet()
                                    .getWorkbook()
                                    .createCellStyle();
        // copy any styles already set
        final CellStyle initialCellStyle = cell.getCellStyle();
        if (initialCellStyle != null) style.cloneStyleFrom(initialCellStyle);
        return style;
    }

    protected CellStyle getStyle(Cell cell) {
        //singleton
        if (this.cellStyle == null) {
            this.cellStyle = this.configureStyle.apply(this.createStyle(cell), cell.getSheet()
                                                                                   .getWorkbook());
        }
        return this.cellStyle;
    }


    @Override
    public ThrowingFunction<ThrowingConsumer<Cell>, ThrowingConsumer<Cell>> function() {
        return cellThrowingConsumer -> cell -> {
            //set the style
            if (this.toggle.apply(this.atomicBoolean))
                cell.setCellStyle(this.getStyle(cell));
            cellThrowingConsumer.accept(cell);
        };
    }
}
