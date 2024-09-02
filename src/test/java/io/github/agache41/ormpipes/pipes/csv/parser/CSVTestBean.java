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

package io.github.agache41.ormpipes.pipes.csv.parser;

import io.github.agache41.annotator.annotations.Position;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.PositionMethod;
import io.github.agache41.ormpipes.pipes.csv.csvField.CSVAccessor;
import io.github.agache41.ormpipes.pipes.csv.csvFile.CSVFile;
import io.github.agache41.ormpipes.pipes.encoding.IOEncoding;
import io.github.agache41.ormpipes.pipes.iostream.IOStream;
import io.github.agache41.ormpipes.pipes.numeric.typeInteger.TypeInteger;
import io.github.agache41.ormpipes.pipes.typeFile.TypeFile;
import io.github.agache41.ormpipes.pipes.typeString.TypeString;
import io.github.agache41.ormpipes.pipes.zip.zipArchive.Zip;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeFile.New
@IOStream.FileBased
@Zip.Archive
@Zip.Entry(fileName = "data.csv")
@IOEncoding.Automatic
@TypeString.IOStreamLines(separator = TypeString.IOStreamLines.CR)
@TypeString.Array(separator = ";")
@CSVFile(positionMethod = PositionMethod.Fields)
public class CSVTestBean {
    @TypeString.toUpperCase(enabledOn = "read")
    @TypeString.toLowerCase(enabledOn = "read")
    @TypeString.trim
    @TypeString.replace(enabledOn = "read", value = "3", with = "3")
    @TypeString.replaceAll(enabledOn = "read", value = "3", with = "3")
    @CSVAccessor(name = "Column1")
    @Position(0)
    private String column1;
    @CSVAccessor(name = "Column2", position = 1)
    @Position(1)
    private String column2;
    @Position(2)
    @TypeInteger.New
    @CSVAccessor(name = "Column3", position = 2)
    private int column3;
}
