package io.github.agache41.ormpipes.pipes.spreadSheet.xslx.multisheet;

import io.github.agache41.ormpipes.pipes.baseTest.values.BaseExcelTestBean;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;


@SpreadSheet.select(sheetName = "ExcelTestBean2")
@SpreadSheet.sheet
public class ExcelSheetTestBean2 extends BaseExcelTestBean {

}