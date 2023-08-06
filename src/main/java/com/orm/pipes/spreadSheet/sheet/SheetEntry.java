package com.orm.pipes.spreadSheet.sheet;

import com.orm.functional.ThrowingConsumer;
import com.orm.functional.ThrowingFunction;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipes.base.othogonal.OrthogonalEntry;
import com.orm.pipes.base.othogonal.enums.NamingMethod;
import com.orm.pipes.base.othogonal.enums.PositionMethod;
import com.orm.pipes.spreadSheet.SpreadSheet;
import com.orm.pipes.spreadSheet.base.EmptyStyleProvider;
import com.orm.reflection.accessor.Accessor;
import org.apache.poi.ss.usermodel.*;

import java.lang.annotation.Annotation;
import java.util.function.BiFunction;

public class SheetEntry extends OrthogonalEntry<SpreadSheet.Header, Cell, ThrowingConsumer<Object>, Object, ThrowingConsumer<Cell>> {

    public SheetEntry(String name, int position) {
        super(name, position);
        this.readPipe = new AnnotablePipe<Annotation, Cell, ThrowingConsumer<Object>>() {
            @Override
            public ThrowingFunction<Cell, ThrowingConsumer<Object>> function() {
                return s -> o -> {
                };
            }

            @Override
            public void configure(Annotation cfg) {
            }
        };

        this.writePipe = new AnnotablePipe<Annotation, Object, ThrowingConsumer<Cell>>() {
            @Override
            public ThrowingFunction<Object, ThrowingConsumer<Cell>> function() {
                return s -> c -> {
                };
            }

            @Override
            public void configure(Annotation cfg) {
            }
        };
    }

    public SheetEntry(Accessor<?> accessor, NamingMethod namingMethod, PositionMethod positionMethod) {
        super(SpreadSheet.Header.class, accessor, namingMethod, positionMethod);
    }

    protected void doPostSheetSettings(Sheet sheet) {
        if (this.cfg == null) return;
        if (this.cfg.autoSizeColumn()) sheet.autoSizeColumn(this.getPosition());
        else if (this.cfg.width() > -1) {
            sheet.setColumnWidth(this.getPosition(), this.cfg.width());
        }
    }

    protected void doHeaderStyle(Cell cell) {
        if (this.cfg == null) return;
        final CellStyle style = cell.getSheet()
                                    .getWorkbook()
                                    .createCellStyle();
        // copy any styles already set
        final CellStyle initialCellStyle = cell.getCellStyle();
        if (initialCellStyle != null) style.cloneStyleFrom(initialCellStyle);

        // apply configured provider
        if (!this.cfg.styleProvider()
                     .equals(EmptyStyleProvider.class)) {
            try {
                BiFunction<CellStyle, Workbook, CellStyle> styleProvider = this.cfg.styleProvider()
                                                                                   .newInstance();
                cell.setCellStyle(styleProvider.apply(style, cell.getSheet()
                                                                 .getWorkbook()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return;
        }

        // apply settings
        style.setAlignment(this.cfg.alignment());
        if (this.cfg.borderBottom() != BorderStyle.NONE) style.setBorderBottom(this.cfg.borderBottom());
        if (this.cfg.borderLeft() != BorderStyle.NONE) style.setBorderLeft(this.cfg.borderLeft());
        if (this.cfg.borderRight() != BorderStyle.NONE) style.setBorderRight(this.cfg.borderRight());
        if (this.cfg.borderTop() != BorderStyle.NONE) style.setBorderTop(this.cfg.borderTop());
        if (this.cfg.bottomBorderColor() != IndexedColors.AUTOMATIC) style.setBottomBorderColor(this.cfg.bottomBorderColor().index);
        if (this.cfg.fillForegroundColor() != IndexedColors.AUTOMATIC) style.setFillForegroundColor(this.cfg.fillForegroundColor().index);
        if (this.cfg.fillBackgroundColor() != IndexedColors.AUTOMATIC) style.setFillBackgroundColor(this.cfg.fillBackgroundColor().index);
        if (this.cfg.fillPattern() != FillPatternType.NO_FILL) style.setFillPattern(this.cfg.fillPattern());
        style.setHidden(this.cfg.hidden());
        if (this.cfg.indention() != -1) style.setIndention(this.cfg.indention());
        if (this.cfg.leftBorderColor() != IndexedColors.AUTOMATIC) style.setLeftBorderColor(this.cfg.leftBorderColor().index);
        style.setLocked(this.cfg.locked());
        style.setQuotePrefixed(this.cfg.quotePrefixed());
        if (this.cfg.rightBorderColor() != IndexedColors.AUTOMATIC) style.setRightBorderColor(this.cfg.rightBorderColor().index);
        if (this.cfg.rotation() != -1) style.setRotation(this.cfg.rotation());
        style.setShrinkToFit(this.cfg.shrinkToFit());
        if (this.cfg.topBorderColor() != IndexedColors.AUTOMATIC) style.setTopBorderColor(this.cfg.topBorderColor().index);
        // if (cfg.verticalAlignment() != VerticalAlignment.DISTRIBUTED)
        style.setVerticalAlignment(this.cfg.verticalAlignment());
        style.setWrapText(this.cfg.wrapText());
        // apply font settings
        Font font = cell.getSheet()
                        .getWorkbook()
                        .createFont();
        font.setBold(this.cfg.bold());
        if (FontCharset.DEFAULT != this.cfg.charSet()) font.setCharSet(this.cfg.charSet()
                                                                               .getValue());
        if (IndexedColors.AUTOMATIC != this.cfg.fontColor()) font.setColor(this.cfg.fontColor().index);
        if (this.cfg.fontHeight() != -1) font.setFontHeight(this.cfg.fontHeight());
        if (this.cfg.fontHeightInPoints() != -1) font.setFontHeightInPoints(this.cfg.fontHeightInPoints());
        if (!this.cfg.fontName()
                     .isEmpty()) font.setFontName(this.cfg.fontName());
        font.setItalic(this.cfg.italic());
        font.setStrikeout(this.cfg.strikeout());
        if (this.cfg.typeOffset() != -1) font.setTypeOffset(this.cfg.typeOffset());
        if (this.cfg.underline() != FontUnderline.NONE) font.setUnderline(this.cfg.underline()
                                                                                  .getByteValue());
        style.setFont(font);

        cell.setCellStyle(style);
    }
}
