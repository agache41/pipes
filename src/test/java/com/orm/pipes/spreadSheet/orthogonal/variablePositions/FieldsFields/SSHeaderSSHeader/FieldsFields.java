package com.orm.pipes.spreadSheet.orthogonal.variablePositions.FieldsFields.SSHeaderSSHeader;

import com.orm.pipes.base.othogonal.enums.Model;
import com.orm.pipes.base.othogonal.enums.NamingMethod;
import com.orm.pipes.base.othogonal.enums.PositionMethod;
import com.orm.pipes.baseTest.values.BaseExcelTestBean;
import com.orm.pipes.iostream.IOStream;
import com.orm.pipes.spreadSheet.SpreadSheet;
import com.orm.pipes.typeFile.TypeFile;

@TypeFile.NewResource
@IOStream.FileBased
@SpreadSheet.xls
@SpreadSheet.select(sheetName = "ExcelTestBean")
@SpreadSheet.sheet(model = Model.VariablePositions, disableLineValidation = true, namingMethod = NamingMethod.JavaFieldNames, positionMethod = PositionMethod.Fields)
public class FieldsFields extends BaseExcelTestBean {
}

