/*
 *    Copyright 2022-2023  Alexandru Agache
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package examples.excel.fileToBean;

import io.github.agache41.annotator.annotations.Position;
import io.github.agache41.ormpipes.pipes.accessor.Accessor;
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
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@IOStream.FileBased
@SpreadSheet.xlsx
@SpreadSheet.select(sheetName = "ExcelBean")
@SpreadSheet.sheet
public class ExcelFileBean {
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