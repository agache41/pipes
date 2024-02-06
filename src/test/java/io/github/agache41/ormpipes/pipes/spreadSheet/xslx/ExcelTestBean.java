package io.github.agache41.ormpipes.pipes.spreadSheet.xslx;

import io.github.agache41.ormpipes.pipes.baseTest.values.BaseExcelTestBean;
import io.github.agache41.ormpipes.pipes.iostream.IOStream;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import io.github.agache41.ormpipes.pipes.typeFile.TypeFile;


@TypeFile.NewResource
@IOStream.FileBased
@SpreadSheet.xlsx
@SpreadSheet.select(sheetName = "ExcelTestBean")
@SpreadSheet.sheet
public class ExcelTestBean extends BaseExcelTestBean {

}
