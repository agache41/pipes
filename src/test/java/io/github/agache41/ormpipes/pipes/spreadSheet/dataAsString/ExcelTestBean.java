package io.github.agache41.ormpipes.pipes.spreadSheet.dataAsString;

import io.github.agache41.annotator.annotations.Position;
import io.github.agache41.ormpipes.pipes.accessor.Accessor;
import io.github.agache41.ormpipes.pipes.baseTest.values.TestBean;
import io.github.agache41.ormpipes.pipes.iostream.IOStream;
import io.github.agache41.ormpipes.pipes.numeric.typeDouble.TypeDouble;
import io.github.agache41.ormpipes.pipes.numeric.typeFloat.TypeFloat;
import io.github.agache41.ormpipes.pipes.numeric.typeInteger.TypeInteger;
import io.github.agache41.ormpipes.pipes.numeric.typeLong.TypeLong;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import io.github.agache41.ormpipes.pipes.temporal.typeDate.TypeDate;
import io.github.agache41.ormpipes.pipes.temporal.typeLocalDate.TypeLocalDate;
import io.github.agache41.ormpipes.pipes.typeFile.TypeFile;
import io.github.agache41.ormpipes.pipes.typeString.TypeString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeFile.NewResource
@IOStream.FileBased
@SpreadSheet.xls
@SpreadSheet.select(sheetName = "ExcelTestBean")
@SpreadSheet.sheet(autoResizeAllColumns = true)
public class ExcelTestBean implements TestBean {

    @Position(0)
    @SpreadSheet.Header(name = "string0", position = 0)
    @TypeString.cellValue
    @TypeString.nullable
    @Accessor
    private String string0;

    @Position(1)
    @SpreadSheet.Header(name = "integer1", position = 1, required = true)
    @TypeString.cellValue
    @TypeInteger.New(simple = true)
    @Accessor
    private Integer integer1;

    @Position(2)
    @SpreadSheet.Header(name = "long2", position = 2)
    @TypeString.cellValue
    @TypeLong.New(simple = true)
    @Accessor
    private Long long2;

    @Position(3)
    @SpreadSheet.Header(name = "double3", position = 3, required = true)
    @TypeString.cellValue
    @TypeDouble.New(simple = true)
    @Accessor
    private Double double3;

    @Position(4)
    @SpreadSheet.Header(name = "float4", position = 4)
    @TypeString.cellValue
    @TypeFloat.New(simple = true)
    @Accessor
    private Float float4;

    @Position(5)
    @SpreadSheet.Header(name = "localdate5", position = 5)
    @TypeString.cellValue
    @TypeLocalDate.New(format = "ISO_LOCAL_DATE")
    @Accessor
    private LocalDate localdate5;

    @Position(6)
    @SpreadSheet.Header(name = "date6", position = 6)
    @TypeString.cellValue
    @TypeDate.New(format = "yyyy.MM.dd")
    @Accessor
    private Date date6;

    @Position(7)
    @SpreadSheet.Header(name = "list7", position = 7)
    @TypeString.cellValue
    @TypeString.List(separator = ",")
    @Accessor
    private List<String> list7;

}
