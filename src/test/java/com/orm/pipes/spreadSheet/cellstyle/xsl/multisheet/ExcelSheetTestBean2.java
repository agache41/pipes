package com.orm.pipes.spreadSheet.cellstyle.xsl.multisheet;

import com.orm.pipes.baseTest.values.BaseExcelStyledTestBean;
import com.orm.pipes.spreadSheet.SpreadSheet;

@SpreadSheet.select(sheetName = "ExcelTestBean2")
@SpreadSheet.sheet(offsetY = 4, offsetX = 3, defaultRowHeightInPoints = 40, defaultColumnWidth = 40, autoResizeAllColumns = true)
public class ExcelSheetTestBean2 extends BaseExcelStyledTestBean {
}
