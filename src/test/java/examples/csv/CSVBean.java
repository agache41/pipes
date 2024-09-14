package examples.csv;

import io.github.agache41.annotator.annotations.Position;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.Model;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.NamingMethod;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.PositionMethod;
import io.github.agache41.ormpipes.pipes.csv.csvField.CSVAccessor;
import io.github.agache41.ormpipes.pipes.csv.csvFile.CSVFile;
import io.github.agache41.ormpipes.pipes.encoding.IOEncoding;
import io.github.agache41.ormpipes.pipes.iostream.IOStream;
import io.github.agache41.ormpipes.pipes.numeric.typeDouble.TypeDouble;
import io.github.agache41.ormpipes.pipes.numeric.typeFloat.TypeFloat;
import io.github.agache41.ormpipes.pipes.numeric.typeInteger.TypeInteger;
import io.github.agache41.ormpipes.pipes.numeric.typeLong.TypeLong;
import io.github.agache41.ormpipes.pipes.temporal.typeDate.TypeDate;
import io.github.agache41.ormpipes.pipes.temporal.typeLocalDate.TypeLocalDate;
import io.github.agache41.ormpipes.pipes.typeFile.TypeFile;
import io.github.agache41.ormpipes.pipes.typeString.TypeString;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@TypeFile.NewResource
@IOStream.FileBased
@IOEncoding.Automatic
@TypeString.IOStreamLines(separator = TypeString.IOStreamLines.CR)
@TypeString.Array(separator = ";")
@CSVFile(namingMethod = NamingMethod.JavaFieldNames, positionMethod = PositionMethod.Fields, model = Model.Fixed)
public class CSVBean {
    @Position(0)
    @TypeString.nullable
    @CSVAccessor(name = "string0", position = 0)
    private String string0;
    
    @Position(1)
    @TypeInteger.New(simple = true)
    @CSVAccessor(name = "integer1", position = 1, required = true)
    private Integer integer1;
    
    @Position(2)
    @TypeLong.New(simple = true)
    @CSVAccessor(name = "long2", position = 2)
    private Long long2;
    
    @Position(3)
    @TypeDouble.New(simple = true)
    @CSVAccessor(name = "double3", position = 3, required = true)
    private Double double3;

    @Position(4)
    @TypeFloat.New(simple = true)
    @CSVAccessor(name = "float4", position = 4)
    private Float float4;

    @Position(5)
    @TypeLocalDate.New(simple = true)
    @CSVAccessor(name = "localdate5", position = 5)
    private LocalDate localdate5;

    @Position(6)
    @TypeDate.New(format = "yyyy-MM-dd")
    @CSVAccessor(name = "date6", position = 6)
    private Date date6;

    @Position(7)
    @TypeString.nullable
    @TypeString.List(separator = ",")
    @CSVAccessor(name = "list7", position = 7)
    private List<String> list7;
}