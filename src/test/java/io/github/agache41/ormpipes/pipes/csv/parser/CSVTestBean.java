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
