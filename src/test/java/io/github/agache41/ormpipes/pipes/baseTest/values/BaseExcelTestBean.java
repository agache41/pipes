package io.github.agache41.ormpipes.pipes.baseTest.values;

import io.github.agache41.annotator.annotations.Position;
import io.github.agache41.ormpipes.pipes.accessor.Accessor;
import io.github.agache41.ormpipes.pipes.numeric.typeDouble.TypeDouble;
import io.github.agache41.ormpipes.pipes.numeric.typeFloat.TypeFloat;
import io.github.agache41.ormpipes.pipes.numeric.typeInteger.TypeInteger;
import io.github.agache41.ormpipes.pipes.numeric.typeLong.TypeLong;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import io.github.agache41.ormpipes.pipes.temporal.typeDate.TypeDate;
import io.github.agache41.ormpipes.pipes.temporal.typeLocalDate.TypeLocalDate;
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
public class BaseExcelTestBean implements TestBean {
    @Position(0)
    @SpreadSheet.Header(name = "string0", position = 0)
    @TypeString.cellValue
    @TypeString.nullable
    @Accessor
    private String string0;

    @Position(1)
    @SpreadSheet.Header(name = "integer1", position = 1, required = true)
    @TypeInteger.cellValue
    @Accessor
    private Integer integer1;

    @Position(2)
    @SpreadSheet.Header(name = "long2", position = 2)
    @TypeLong.cellValue
    @Accessor
    private Long long2;

    @Position(3)
    @SpreadSheet.Header(name = "double3", position = 3, required = true)
    @TypeDouble.cellValue
    @Accessor
    private Double double3;

    @Position(4)
    @SpreadSheet.Header(name = "float4", position = 4)
    @TypeFloat.cellValue
    @Accessor
    private Float float4;

    @Position(5)
    @SpreadSheet.Header(name = "localdate5", position = 5)
    @TypeLocalDate.cellValue
    @Accessor
    private LocalDate localdate5;

    @Position(6)
    @SpreadSheet.Header(name = "date6", position = 6)
    @TypeDate.cellValue
    @Accessor
    private Date date6;

    @Position(7)
    @SpreadSheet.Header(name = "list7", position = 7)
    @TypeString.cellValue
    @TypeString.List(separator = ",")
    @Accessor
    private List<String> list7;
}
