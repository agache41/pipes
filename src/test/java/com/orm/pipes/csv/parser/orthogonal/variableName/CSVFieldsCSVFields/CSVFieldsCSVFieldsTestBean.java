package com.orm.pipes.csv.parser.orthogonal.variableName.CSVFieldsCSVFields;

import com.orm.pipes.base.othogonal.enums.Model;
import com.orm.pipes.baseTest.values.BaseCSVTestBean;
import com.orm.pipes.csv.csvFile.CSVFile;
import com.orm.pipes.encoding.IOEncoding;
import com.orm.pipes.iostream.IOStream;
import com.orm.pipes.typeFile.TypeFile;
import com.orm.pipes.typeString.TypeString;

import static com.orm.pipes.typeString.TypeString.IOStreamLines.CR;

@TypeFile.NewResource
@IOStream.FileBased
@IOEncoding.Automatic
@TypeString.IOStreamLines(separator = CR)
@TypeString.Array(separator = ";")
@CSVFile(model = Model.VariableNames)
public class CSVFieldsCSVFieldsTestBean extends BaseCSVTestBean {
}

