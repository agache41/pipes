package io.github.agache41.ormpipes.pipes.spreadSheet.xlsx;

import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.OutputStream;

/**
 * <pre>
 * The type Xlsx writer.
 * </pre>
 */
public class XLSXWriter implements AnnotablePipe<SpreadSheet.xlsx, ThrowingConsumer<XSSFWorkbook>, ThrowingConsumer<OutputStream>> {
    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(SpreadSheet.xlsx cfg) {
    }

    /**
     * {@inheritDoc}
     */
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
