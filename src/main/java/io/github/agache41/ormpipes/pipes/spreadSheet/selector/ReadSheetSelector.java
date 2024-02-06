package io.github.agache41.ormpipes.pipes.spreadSheet.selector;

import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * <pre>
 * The type Read sheet selector.
 * </pre>
 */
public class ReadSheetSelector implements AnnotablePipe<SpreadSheet.select, Workbook, Sheet> {

    private String sheetName;
    private int sheetIndex;

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(SpreadSheet.select cfg) {
        this.sheetName = cfg.sheetName();
        this.sheetIndex = cfg.sheetIndex();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ThrowingFunction<Workbook, Sheet> function() {
        return this::readSheet;
    }

    /**
     * <pre>
     * Read sheet sheet.
     * </pre>
     *
     * @param workbook the workbook
     * @return the sheet
     */
    protected Sheet readSheet(Workbook workbook) {
        Sheet sheet;
        if (this.sheetName.isEmpty()) {
            sheet = workbook.getSheetAt(this.sheetIndex);
        } else {
            sheet = workbook.getSheet(this.sheetName);
        }
        if (sheet == null) {
            throw new RuntimeException(" No Sheet " + (this.sheetName.isEmpty() ? "at index " + this.sheetIndex : "named " + this.sheetName + " found!"));
        }
        return sheet;
    }
}

