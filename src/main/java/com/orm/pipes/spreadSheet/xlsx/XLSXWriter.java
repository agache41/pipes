package com.orm.pipes.spreadSheet.xlsx;

import com.orm.functional.ThrowingConsumer;
import com.orm.functional.ThrowingFunction;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipes.spreadSheet.SpreadSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.OutputStream;

public class XLSXWriter implements AnnotablePipe<SpreadSheet.xlsx, ThrowingConsumer<XSSFWorkbook>, ThrowingConsumer<OutputStream>> {
    @Override
    public void configure(SpreadSheet.xlsx cfg) {
    }

    @Override
    public ThrowingFunction<ThrowingConsumer<XSSFWorkbook>, ThrowingConsumer<OutputStream>> function() {
        return workbookThrowingConsumer -> outputStream -> {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
            workbookThrowingConsumer.accept(xssfWorkbook);
            xssfWorkbook.write(outputStream);
            xssfWorkbook.close();
            outputStream.close();
        };
    }
}
