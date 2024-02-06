package io.github.agache41.ormpipes.pipes.spreadSheet.sheet;

import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.stream.Stream;

import static io.github.agache41.ormpipes.functional.ThrowingConsumer.wrap;

/**
 * <pre>
 * The type Sheet writer.
 * </pre>
 */
public class SheetWriter extends SheetBase implements AnnotablePipe<SpreadSheet.sheet, Stream<?>, ThrowingConsumer<Sheet>> {

    /**
     * {@inheritDoc}
     */
    @Override
    public StrongType getInputType() {
        return StrongType.of(Stream.class)
                         .parameterizedWith(this.onClass);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StrongType getOutputType() {
        return StrongType.of(ThrowingConsumer.class)
                         .parameterizedWith(Sheet.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ThrowingFunction<Stream<?>, ThrowingConsumer<Sheet>> function() {
        return stream -> sheet -> {
            final SheetHandler handler = this.handler();
            handler.validate();
            this.doPreSheetSettings(sheet, handler);
            if (this.append) {
                handler.lineNumber = sheet.getLastRowNum() + 1;
            } else if (handler.useFirstLineAsHeader) {
                this.doHeader(handler, sheet);
            }
            stream.forEach(wrap(object -> this.doRow(handler, sheet, object)));
            this.doPostSheetSettings(sheet, handler);
        };
    }

    private void doHeader(SheetHandler handler,
                          Sheet sheet) throws Throwable {
        if (!handler.validHeader) {
            throw new RuntimeException(" Header is not available for writing the first line !" + handler);
        }
        Row result = sheet.createRow(handler.lineNumber++);
        this.doRowSettings(result);
        for (int index = 0; index < handler.header.length; index++) {
            Cell cell = result.createCell(index + this.offsetX);
            cell.setCellValue(handler.header[index]);
            handler.entryList.get(index)
                             .doHeaderStyle(cell);
        }
    }

    private void doRow(SheetHandler handler,
                       Sheet sheet,
                       Object input) throws Throwable {
        Row result = sheet.createRow(handler.lineNumber++);
        this.doRowSettings(result);
        for (int index = 0; index < handler.positionIndex.length; index++) {
            ThrowingConsumer<Cell> cellConsumer = handler.writePipes[index].apply(input);
            Cell cell = result.createCell(handler.positionIndex[index]);
            cellConsumer.accept(cell);
        }
    }

    private void doRowSettings(Row row) {
        if (this.defaultRowHeight > -1) {
            row.setHeight(this.defaultRowHeight);
        }
        if (this.defaultRowHeightInPoints > -1) {
            row.setHeightInPoints(this.defaultRowHeightInPoints);
        }
    }

    private void doPreSheetSettings(Sheet sheet,
                                    SheetHandler handler) {
        if (this.defaultColumnWidth > -1) {
            sheet.setDefaultColumnWidth(this.defaultColumnWidth);
        }
        if (this.defaultRowHeight > -1) {
            sheet.setDefaultRowHeight(this.defaultRowHeight);
        }
        if (this.defaultRowHeightInPoints > -1) {
            sheet.setDefaultRowHeightInPoints(this.defaultRowHeightInPoints);
        }
        if (this.offsetY > 0) {
            for (int index = 0; index < this.offsetY; index++)
                this.doRowSettings(sheet.createRow(handler.lineNumber++));
        }
        if (this.offsetX > 0) {
            for (int index = 0; index < handler.positionIndex.length; index++)
                handler.positionIndex[index] += this.offsetX;
            for (SheetEntry sheetEntry : handler.entryList)
                sheetEntry.setPosition(sheetEntry.getPosition() + this.offsetX);
        } else {
            this.offsetX = 0;
        }
    }

    private void doPostSheetSettings(Sheet sheet,
                                     SheetHandler handler) {
        if (this.autoResizeAllColumns) {
            for (int index = 0; index < handler.positionIndex.length; index++)
                sheet.autoSizeColumn(handler.positionIndex[index]);
        } else {
            for (SheetEntry sheetEntry : handler.entryList)
                sheetEntry.doPostSheetSettings(sheet);
        }
    }
}



