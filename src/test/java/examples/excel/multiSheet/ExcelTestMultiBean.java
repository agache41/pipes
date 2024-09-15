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

package examples.excel.multiSheet;

import io.github.agache41.ormpipes.pipes.accessor.Accessor;
import io.github.agache41.ormpipes.pipes.field.Field;
import io.github.agache41.ormpipes.pipes.iostream.IOStream;
import io.github.agache41.ormpipes.pipes.pipe.Pipe;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import io.github.agache41.ormpipes.pipes.typeFile.TypeFile;
import lombok.Data;

import java.util.stream.Stream;

@Data
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
