package io.github.agache41.ormpipes.pipes.spreadSheet.sheet;

import io.github.agache41.annotator.accessor.Accessor;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.base.othogonal.OrthogonalEntry;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.NamingMethod;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.PositionMethod;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import io.github.agache41.ormpipes.pipes.spreadSheet.base.EmptyStyleProvider;
import org.apache.poi.ss.usermodel.*;

import java.lang.annotation.Annotation;
import java.util.function.BiFunction;

/**
 * <pre>
 * The type Sheet entry.
 * </pre>
 */
public class SheetEntry extends OrthogonalEntry<SpreadSheet.Header, Cell, ThrowingConsumer<Object>, Object, ThrowingConsumer<Cell>> {

    /**
     * <pre>
     * Instantiates a new Sheet entry.
     * </pre>
     *
     * @param name     the name
     * @param position the position
     */
    public SheetEntry(String name,
                      int position) {
        super(name, position);
        this.readPipe = new AnnotablePipe<Annotation, Cell, ThrowingConsumer<Object>>() {
            /**
             * {@inheritDoc}
             */
            @Override
            public ThrowingFunction<Cell, ThrowingConsumer<Object>> function() {
                return s -> o -> {
                };
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void configure(Annotation cfg) {
            }
        };

        this.writePipe = new AnnotablePipe<Annotation, Object, ThrowingConsumer<Cell>>() {
            /**
             * {@inheritDoc}
             */
            @Override
            public ThrowingFunction<Object, ThrowingConsumer<Cell>> function() {
                return s -> c -> {
                };
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void configure(Annotation cfg) {
            }
        };
    }

    /**
     * <pre>
     * Instantiates a new Sheet entry.
     * </pre>
     *
     * @param accessor       the accessor
     * @param namingMethod   the naming method
     * @param positionMethod the position method
     */
    public SheetEntry(Accessor<?> accessor,
                      NamingMethod namingMethod,
                      PositionMethod positionMethod) {
        super(SpreadSheet.Header.class, accessor, namingMethod, positionMethod);
    }

    /**
     * <pre>
     * Do post sheet settings.
     * </pre>
     *
     * @param sheet the sheet
     */
    protected void doPostSheetSettings(Sheet sheet) {
        if (this.cfg == null) {
            return;
        }
        if (this.cfg.autoSizeColumn()) {
            sheet.autoSizeColumn(this.getPosition());
        } else if (this.cfg.width() > -1) {
            sheet.setColumnWidth(this.getPosition(), this.cfg.width());
        }
    }

    /**
     * <pre>
     * Do header style.
     * </pre>
     *
     * @param cell the cell
     */
    protected void doHeaderStyle(Cell cell) {
        if (this.cfg == null) {
            return;
        }
        final CellStyle style = cell.getSheet()
                                    .getWorkbook()
                                    .createCellStyle();
        // copy any styles already set
        final CellStyle initialCellStyle = cell.getCellStyle();
        if (initialCellStyle != null) {
            style.cloneStyleFrom(initialCellStyle);
        }

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
        if (this.cfg.borderBottom() != BorderStyle.NONE) {
            style.setBorderBottom(this.cfg.borderBottom());
        }
        if (this.cfg.borderLeft() != BorderStyle.NONE) {
            style.setBorderLeft(this.cfg.borderLeft());
        }
        if (this.cfg.borderRight() != BorderStyle.NONE) {
            style.setBorderRight(this.cfg.borderRight());
        }
        if (this.cfg.borderTop() != BorderStyle.NONE) {
            style.setBorderTop(this.cfg.borderTop());
        }
        if (this.cfg.bottomBorderColor() != IndexedColors.AUTOMATIC) {
            style.setBottomBorderColor(this.cfg.bottomBorderColor().index);
        }
        if (this.cfg.fillForegroundColor() != IndexedColors.AUTOMATIC) {
            style.setFillForegroundColor(this.cfg.fillForegroundColor().index);
        }
        if (this.cfg.fillBackgroundColor() != IndexedColors.AUTOMATIC) {
            style.setFillBackgroundColor(this.cfg.fillBackgroundColor().index);
        }
        if (this.cfg.fillPattern() != FillPatternType.NO_FILL) {
            style.setFillPattern(this.cfg.fillPattern());
        }
        style.setHidden(this.cfg.hidden());
        if (this.cfg.indention() != -1) {
            style.setIndention(this.cfg.indention());
        }
        if (this.cfg.leftBorderColor() != IndexedColors.AUTOMATIC) {
            style.setLeftBorderColor(this.cfg.leftBorderColor().index);
        }
        style.setLocked(this.cfg.locked());
        style.setQuotePrefixed(this.cfg.quotePrefixed());
        if (this.cfg.rightBorderColor() != IndexedColors.AUTOMATIC) {
            style.setRightBorderColor(this.cfg.rightBorderColor().index);
        }
        if (this.cfg.rotation() != -1) {
            style.setRotation(this.cfg.rotation());
        }
        style.setShrinkToFit(this.cfg.shrinkToFit());
        if (this.cfg.topBorderColor() != IndexedColors.AUTOMATIC) {
            style.setTopBorderColor(this.cfg.topBorderColor().index);
        }
        // if (cfg.verticalAlignment() != VerticalAlignment.DISTRIBUTED)
        style.setVerticalAlignment(this.cfg.verticalAlignment());
        style.setWrapText(this.cfg.wrapText());
        // apply font settings
        Font font = cell.getSheet()
                        .getWorkbook()
                        .createFont();
        font.setBold(this.cfg.bold());
        if (FontCharset.DEFAULT != this.cfg.charSet()) {
            font.setCharSet(this.cfg.charSet()
                                    .getValue());
        }
        if (IndexedColors.AUTOMATIC != this.cfg.fontColor()) {
            font.setColor(this.cfg.fontColor().index);
        }
        if (this.cfg.fontHeight() != -1) {
            font.setFontHeight(this.cfg.fontHeight());
        }
        if (this.cfg.fontHeightInPoints() != -1) {
            font.setFontHeightInPoints(this.cfg.fontHeightInPoints());
        }
        if (!this.cfg.fontName()
                     .isEmpty()) {
            font.setFontName(this.cfg.fontName());
        }
        font.setItalic(this.cfg.italic());
        font.setStrikeout(this.cfg.strikeout());
        if (this.cfg.typeOffset() != -1) {
            font.setTypeOffset(this.cfg.typeOffset());
        }
        if (this.cfg.underline() != FontUnderline.NONE) {
            font.setUnderline(this.cfg.underline()
                                      .getByteValue());
        }
        style.setFont(font);

        cell.setCellStyle(style);
    }
}
