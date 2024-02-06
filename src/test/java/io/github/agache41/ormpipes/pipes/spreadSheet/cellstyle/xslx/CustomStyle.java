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
