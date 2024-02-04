package io.github.agache41.ormpipes.pipes.spreadSheet.file;

import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class SpreadSheetFileReader implements AnnotablePipe<SpreadSheet.file, File, Workbook> {
    @Override
    public void configure(SpreadSheet.file cfg) {

    }

    @Override
    public ThrowingFunction<File, Workbook> function() {
        return file -> {
            try (InputStream is = new FileInputStream(file)) {
                return WorkbookFactory.create(is);
            } catch (Throwable e) {
                logger.error(e);
                throw e;
            }
        };
    }
}
