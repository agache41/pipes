package com.orm.pipes.spreadSheet.cellstyle.xslx.multisheet;

import com.orm.pipes.accessor.Accessor;
import com.orm.pipes.field.Field;
import com.orm.pipes.iostream.IOStream;
import com.orm.pipes.pipe.Pipe;
import com.orm.pipes.spreadSheet.SpreadSheet;
import com.orm.pipes.typeFile.TypeFile;
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
@SpreadSheet.xlsx
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
