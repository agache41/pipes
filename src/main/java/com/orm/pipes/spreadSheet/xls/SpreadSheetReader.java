package com.orm.pipes.spreadSheet.xls;

import com.orm.functional.ThrowingFunction;
import com.orm.pipe.AnnotablePipe;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.InputStream;
import java.lang.annotation.Annotation;

public class SpreadSheetReader implements AnnotablePipe<Annotation, InputStream, Workbook> {

    @Override
    public void configure(Annotation cfg) {

    }

    @Override
    public ThrowingFunction<InputStream, Workbook> function() {
        return inputStream -> {
            try (InputStream is = inputStream) {
                return WorkbookFactory.create(is);
            } catch (Throwable e) {
                logger.error(e);
                throw e;
            }
        };
    }
}
