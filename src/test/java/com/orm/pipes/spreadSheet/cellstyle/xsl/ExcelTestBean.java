package com.orm.pipes.spreadSheet.cellstyle.xsl;

import com.orm.pipes.baseTest.values.BaseExcelStyledTestBean;
import com.orm.pipes.iostream.IOStream;
import com.orm.pipes.spreadSheet.SpreadSheet;
import com.orm.pipes.typeFile.TypeFile;

@TypeFile.NewResource
@IOStream.FileBased
@SpreadSheet.xls
@SpreadSheet.select(sheetName = "ExcelTestBean")
@SpreadSheet.sheet(offsetY = 4, offsetX = 3, defaultColumnWidth = 30, defaultRowHeightInPoints = 30)
public class ExcelTestBean extends BaseExcelStyledTestBean {
}
