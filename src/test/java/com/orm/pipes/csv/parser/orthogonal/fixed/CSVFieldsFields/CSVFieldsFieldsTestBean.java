package com.orm.pipes.csv.parser.orthogonal.fixed.CSVFieldsFields;

import com.orm.pipes.base.othogonal.enums.Model;
import com.orm.pipes.base.othogonal.enums.PositionMethod;
import com.orm.pipes.baseTest.values.BaseCSVTestBean;
import com.orm.pipes.csv.csvFile.CSVFile;
import com.orm.pipes.encoding.IOEncoding;
import com.orm.pipes.iostream.IOStream;
import com.orm.pipes.typeFile.TypeFile;
import com.orm.pipes.typeString.TypeString;

@TypeFile.NewResource
@IOStream.FileBased
@IOEncoding.Automatic
@TypeString.IOStreamLines(separator = TypeString.IOStreamLines.CR)
@TypeString.Array(separator = ";")
@CSVFile(positionMethod = PositionMethod.Fields, model = Model.Fixed)
public class CSVFieldsFieldsTestBean extends BaseCSVTestBean {
}

