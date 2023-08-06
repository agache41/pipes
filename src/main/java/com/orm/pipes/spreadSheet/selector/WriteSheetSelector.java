package com.orm.pipes.spreadSheet.selector;

import com.orm.functional.ThrowingFunction;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipes.spreadSheet.SpreadSheet;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class WriteSheetSelector implements AnnotablePipe<SpreadSheet.select, Workbook, Sheet> {

    private String sheetName;

    private int sheetIndex;

    @Override
    public void configure(SpreadSheet.select cfg) {
        this.sheetName = cfg.sheetName();
        this.sheetIndex = cfg.sheetIndex();
    }

    @Override
    public ThrowingFunction<Workbook, Sheet> function() {
        return workbook -> {
            Sheet sheet;
            if (this.sheetName.isEmpty())
                try {
                    sheet = workbook.getSheetAt(this.sheetIndex);
                } catch (IllegalArgumentException iae) {
                    sheet = workbook.createSheet();
                }
            else {
                sheet = workbook.getSheet(this.sheetName);
                if (sheet == null)
                    sheet = workbook.createSheet(this.sheetName);
            }
            return sheet;
        };
    }
}



