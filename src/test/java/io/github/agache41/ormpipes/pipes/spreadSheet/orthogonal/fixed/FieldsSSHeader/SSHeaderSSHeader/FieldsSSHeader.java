package io.github.agache41.ormpipes.pipes.spreadSheet.orthogonal.fixed.FieldsSSHeader.SSHeaderSSHeader;

import io.github.agache41.ormpipes.pipes.base.othogonal.enums.Model;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.NamingMethod;
import io.github.agache41.ormpipes.pipes.baseTest.values.BaseExcelTestBean;
import io.github.agache41.ormpipes.pipes.iostream.IOStream;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import io.github.agache41.ormpipes.pipes.typeFile.TypeFile;

@TypeFile.NewResource
@IOStream.FileBased
@SpreadSheet.xls
@SpreadSheet.select(sheetName = "ExcelTestBean")
@SpreadSheet.sheet(model = Model.Fixed, disableLineValidation = true, namingMethod = NamingMethod.JavaFieldNames)
public class FieldsSSHeader extends BaseExcelTestBean {
}

