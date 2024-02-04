package io.github.agache41.ormpipes.pipes.spreadSheet.cellstyle.xsl;

import io.github.agache41.ormpipes.pipes.baseTest.values.BaseExcelStyledTestBean;
import io.github.agache41.ormpipes.pipes.iostream.IOStream;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import io.github.agache41.ormpipes.pipes.typeFile.TypeFile;

@TypeFile.NewResource
@IOStream.FileBased
@SpreadSheet.xls
@SpreadSheet.select(sheetName = "ExcelTestBean")
@SpreadSheet.sheet(offsetY = 4, offsetX = 3, defaultColumnWidth = 30, defaultRowHeightInPoints = 30)
public class ExcelTestBean extends BaseExcelStyledTestBean {
}
