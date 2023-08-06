package com.orm.pipes.spreadSheet.orthogonal.variablePositions.FieldsSSHeader.SSHeaderSSHeader;

import com.orm.pipes.base.othogonal.enums.Model;
import com.orm.pipes.base.othogonal.enums.NamingMethod;
import com.orm.pipes.baseTest.values.BaseExcelTestBean;
import com.orm.pipes.iostream.IOStream;
import com.orm.pipes.spreadSheet.SpreadSheet;
import com.orm.pipes.typeFile.TypeFile;

@TypeFile.NewResource
@IOStream.FileBased
@SpreadSheet.xls
@SpreadSheet.select(sheetName = "ExcelTestBean")
@SpreadSheet.sheet(model = Model.VariablePositions, disableLineValidation = true, namingMethod = NamingMethod.JavaFieldNames)
public class FieldsSSHeader extends BaseExcelTestBean {
}

