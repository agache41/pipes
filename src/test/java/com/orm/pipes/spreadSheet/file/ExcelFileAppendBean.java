package com.orm.pipes.spreadSheet.file;

import com.orm.pipes.base.othogonal.enums.Model;
import com.orm.pipes.baseTest.values.BaseExcelTestBean;
import com.orm.pipes.spreadSheet.SpreadSheet;

@SpreadSheet.file(view = "append", template = true)
@SpreadSheet.file(view = "noAppend")
@SpreadSheet.select(sheetName = "ExcelTestBean")
@SpreadSheet.sheet(useFirstLineAsHeader = false, model = Model.Fixed, skipFirstNLines = 4, append = true)
public class ExcelFileAppendBean extends BaseExcelTestBean {

}
