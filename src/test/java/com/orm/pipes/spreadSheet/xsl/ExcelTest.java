package com.orm.pipes.spreadSheet.xsl;

import com.orm.pipes.baseTest.ReadWriteTest;
import com.orm.pipes.spreadSheet.SpreadSheet;

class ExcelTest extends ReadWriteTest<ExcelTestBean> {
    public ExcelTest() {
        super.init(ExcelTestBean.class, SpreadSheet.xls.extension);
    }
}