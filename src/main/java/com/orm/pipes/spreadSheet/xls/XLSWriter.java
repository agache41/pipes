package com.orm.pipes.spreadSheet.xls;

import com.orm.functional.ThrowingConsumer;
import com.orm.functional.ThrowingFunction;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipes.spreadSheet.SpreadSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.OutputStream;

public class XLSWriter implements AnnotablePipe<SpreadSheet.xls, ThrowingConsumer<HSSFWorkbook>, ThrowingConsumer<OutputStream>> {
    @Override
    public void configure(SpreadSheet.xls cfg) {
    }

    @Override
    public ThrowingFunction<ThrowingConsumer<HSSFWorkbook>, ThrowingConsumer<OutputStream>> function() {
        return workbookThrowingConsumer -> outputStream -> {
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
            workbookThrowingConsumer.accept(hssfWorkbook);
            hssfWorkbook.write(outputStream);
            hssfWorkbook.close();
            outputStream.close();
        };
    }
}
