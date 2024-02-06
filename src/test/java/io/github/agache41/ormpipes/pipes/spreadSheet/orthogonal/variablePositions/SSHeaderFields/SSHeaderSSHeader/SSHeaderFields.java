package io.github.agache41.ormpipes.pipes.spreadSheet.orthogonal.variablePositions.SSHeaderFields.SSHeaderSSHeader;

import io.github.agache41.ormpipes.pipes.base.othogonal.enums.Model;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.PositionMethod;
import io.github.agache41.ormpipes.pipes.baseTest.values.BaseExcelTestBean;
import io.github.agache41.ormpipes.pipes.iostream.IOStream;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import io.github.agache41.ormpipes.pipes.typeFile.TypeFile;

@TypeFile.NewResource
@IOStream.FileBased
@SpreadSheet.xls
@SpreadSheet.select(sheetName = "ExcelTestBean")
@SpreadSheet.sheet(model = Model.VariablePositions, disableLineValidation = true, positionMethod = PositionMethod.Fields)
public class SSHeaderFields extends BaseExcelTestBean {
}

