package io.github.agache41.ormpipes.pipes.spreadSheet.cellstyle.xsl.multisheet;

import io.github.agache41.ormpipes.pipes.baseTest.values.BaseExcelStyledTestBean;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;

@SpreadSheet.select(sheetName = "ExcelTestBean2")
@SpreadSheet.sheet(offsetY = 4, offsetX = 3, defaultRowHeightInPoints = 40, defaultColumnWidth = 40, autoResizeAllColumns = true)
public class ExcelSheetTestBean2 extends BaseExcelStyledTestBean {
}
