package io.github.agache41.ormpipes.pipes.spreadSheet.base;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.function.BiFunction;

/**
 * <pre>
 * The type Empty style provider.
 * </pre>
 */
public class EmptyStyleProvider implements BiFunction<CellStyle, Workbook, CellStyle> {

    /**
     * <pre>
     * Instantiates a new Empty style provider.
     * </pre>
     */
    public EmptyStyleProvider() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CellStyle apply(CellStyle cellStyle,
                           Workbook workbook) {
        return cellStyle;
    }
}
