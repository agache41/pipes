package io.github.agache41.ormpipes.pipes.spreadSheet.base;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.function.BiFunction;

public class EmptyStyleProvider implements BiFunction<CellStyle, Workbook, CellStyle> {

    public EmptyStyleProvider() {
    }

    @Override
    public CellStyle apply(CellStyle cellStyle, Workbook workbook) {
        return cellStyle;
    }
}
