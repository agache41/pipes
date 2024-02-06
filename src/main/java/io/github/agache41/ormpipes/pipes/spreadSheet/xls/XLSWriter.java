package io.github.agache41.ormpipes.pipes.spreadSheet.xls;

import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.OutputStream;

/**
 * <pre>
 * The type Xls writer.
 * </pre>
 */
public class XLSWriter implements AnnotablePipe<SpreadSheet.xls, ThrowingConsumer<HSSFWorkbook>, ThrowingConsumer<OutputStream>> {
    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(SpreadSheet.xls cfg) {
    }

    /**
     * {@inheritDoc}
     */
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
