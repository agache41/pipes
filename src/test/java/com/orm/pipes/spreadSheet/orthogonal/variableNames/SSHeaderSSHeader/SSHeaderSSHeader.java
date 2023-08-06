package com.orm.pipes.spreadSheet.orthogonal.variableNames.SSHeaderSSHeader;

import com.orm.pipes.base.othogonal.enums.Model;
import com.orm.pipes.baseTest.values.BaseExcelTestBean;
import com.orm.pipes.iostream.IOStream;
import com.orm.pipes.spreadSheet.SpreadSheet;
import com.orm.pipes.typeFile.TypeFile;

@TypeFile.NewResource
@IOStream.FileBased
@SpreadSheet.xls
@SpreadSheet.select(sheetName = "ExcelTestBean")
@SpreadSheet.sheet(model = Model.VariableNames, disableLineValidation = true)
public class SSHeaderSSHeader extends BaseExcelTestBean {
}

