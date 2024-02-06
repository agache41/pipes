package io.github.agache41.ormpipes.pipes.spreadSheet.dataAsString;

import io.github.agache41.ormpipes.pipes.baseTest.ReadWriteTest;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;

class ExcelTest extends ReadWriteTest<ExcelTestBean> {
    public ExcelTest() {
        super.init(ExcelTestBean.class, SpreadSheet.xls.extension);
    }
}