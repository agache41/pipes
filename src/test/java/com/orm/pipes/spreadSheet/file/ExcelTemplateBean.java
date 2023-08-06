package com.orm.pipes.spreadSheet.file;

import com.orm.pipes.baseTest.values.BaseExcelTestBean;
import com.orm.pipes.iostream.IOStream;
import com.orm.pipes.spreadSheet.SpreadSheet;
import com.orm.pipes.typeFile.TypeFile;

@TypeFile.NewResource
@IOStream.FileBased
@SpreadSheet.xls
@SpreadSheet.select(sheetName = "ExcelTestBean")
@SpreadSheet.sheet
public class ExcelTemplateBean extends BaseExcelTestBean {

}
