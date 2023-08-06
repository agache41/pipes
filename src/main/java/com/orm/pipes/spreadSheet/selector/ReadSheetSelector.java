package com.orm.pipes.spreadSheet.selector;

import com.orm.functional.ThrowingFunction;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipes.spreadSheet.SpreadSheet;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ReadSheetSelector implements AnnotablePipe<SpreadSheet.select, Workbook, Sheet> {

    private String sheetName;
    private int sheetIndex;

    @Override
    public void configure(SpreadSheet.select cfg) {
        this.sheetName = cfg.sheetName();
        this.sheetIndex = cfg.sheetIndex();
    }

    @Override
    public ThrowingFunction<Workbook, Sheet> function() {
        return this::readSheet;
    }

    protected Sheet readSheet(Workbook workbook) {
        Sheet sheet;
        if (this.sheetName.isEmpty()) sheet = workbook.getSheetAt(this.sheetIndex);
        else sheet = workbook.getSheet(this.sheetName);
        if (sheet == null) {
            throw new RuntimeException(" No Sheet " + (this.sheetName.isEmpty() ? "at index " + this.sheetIndex : "named " + this.sheetName + " found!"));
        }
        return sheet;
    }
}

