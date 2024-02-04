package io.github.agache41.ormpipes.pipes.spreadSheet.file;

import io.github.agache41.ormpipes.pipes.base.othogonal.enums.Model;
import io.github.agache41.ormpipes.pipes.baseTest.values.BaseExcelTestBean;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;

@SpreadSheet.file(view = "append", template = true)
@SpreadSheet.file(view = "noAppend")
@SpreadSheet.select(sheetName = "ExcelTestBean")
@SpreadSheet.sheet(useFirstLineAsHeader = false, model = Model.Fixed, skipFirstNLines = 4, append = true)
public class ExcelFileAppendBean extends BaseExcelTestBean {

}
