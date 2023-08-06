package com.orm.pipes.spreadSheet.xsl.multisheet;

import com.orm.pipes.baseTest.values.BaseExcelTestBean;
import com.orm.pipes.spreadSheet.SpreadSheet;


@SpreadSheet.select(sheetName = "ExcelTestBean2")
@SpreadSheet.sheet
public class ExcelSheetTestBean2 extends BaseExcelTestBean {

}