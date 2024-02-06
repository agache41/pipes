package io.github.agache41.ormpipes.pipes.csv.parser.orthogonal.variableName.CSVFieldsCSVFields;

import io.github.agache41.ormpipes.pipes.base.othogonal.enums.Model;
import io.github.agache41.ormpipes.pipes.baseTest.values.BaseCSVTestBean;
import io.github.agache41.ormpipes.pipes.csv.csvFile.CSVFile;
import io.github.agache41.ormpipes.pipes.encoding.IOEncoding;
import io.github.agache41.ormpipes.pipes.iostream.IOStream;
import io.github.agache41.ormpipes.pipes.typeFile.TypeFile;
import io.github.agache41.ormpipes.pipes.typeString.TypeString;

import static io.github.agache41.ormpipes.pipes.typeString.TypeString.IOStreamLines.CR;

@TypeFile.NewResource
@IOStream.FileBased
@IOEncoding.Automatic
@TypeString.IOStreamLines(separator = CR)
@TypeString.Array(separator = ";")
@CSVFile(model = Model.VariableNames)
public class CSVFieldsCSVFieldsTestBean extends BaseCSVTestBean {
}

