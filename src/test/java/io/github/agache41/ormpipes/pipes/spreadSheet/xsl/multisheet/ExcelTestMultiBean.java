package io.github.agache41.ormpipes.pipes.spreadSheet.xsl.multisheet;

import io.github.agache41.ormpipes.pipes.accessor.Accessor;
import io.github.agache41.ormpipes.pipes.field.Field;
import io.github.agache41.ormpipes.pipes.iostream.IOStream;
import io.github.agache41.ormpipes.pipes.pipe.Pipe;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import io.github.agache41.ormpipes.pipes.typeFile.TypeFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.stream.Stream;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeFile.NewResource
@IOStream.FileBased
@SpreadSheet.xls
@Field.forEachAnnotatedWith(Accessor.class)
public class ExcelTestMultiBean {
    @SpreadSheet.select(sheetName = "ExcelTestBean1")
    @Pipe.ofClass(ExcelSheetTestBean1.class)
    @Accessor
    private Stream<ExcelSheetTestBean1> sheet1;
    @Pipe.ofClass(ExcelSheetTestBean2.class)
    @Accessor
    private Stream<ExcelSheetTestBean2> sheet2;
}
