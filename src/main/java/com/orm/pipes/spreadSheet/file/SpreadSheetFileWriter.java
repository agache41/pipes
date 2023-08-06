package com.orm.pipes.spreadSheet.file;

import com.orm.functional.ThrowingConsumer;
import com.orm.functional.ThrowingFunction;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipes.spreadSheet.SpreadSheet;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class SpreadSheetFileWriter implements AnnotablePipe<SpreadSheet.file, ThrowingConsumer<Workbook>, ThrowingConsumer<File>> {
    private boolean template;
    private Type type;

    @Override
    public void configure(SpreadSheet.file cfg) {
        this.template = cfg.template();
        this.type = cfg.type();
    }

    @Override
    public ThrowingFunction<ThrowingConsumer<Workbook>, ThrowingConsumer<File>> function() {
        return workbookThrowingConsumer -> file -> {
            final String extension = FilenameUtils.getExtension(file.getName());
            boolean useHssf = this.type.getType(extension);
            Workbook workbook;
            if (this.template) {
                try (InputStream is = new FileInputStream(file)) {
                    workbook = WorkbookFactory.create(is);
                }
            } else workbook = useHssf ? new HSSFWorkbook() : new XSSFWorkbook();
            workbookThrowingConsumer.accept(workbook);
            try (OutputStream outputStream = new FileOutputStream(file)) {
                workbook.write(outputStream);
            } finally {
                workbook.close();
            }
        };
    }
}
