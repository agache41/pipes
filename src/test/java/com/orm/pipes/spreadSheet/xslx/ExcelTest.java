package com.orm.pipes.spreadSheet.xslx;

import com.orm.pipes.baseTest.ReadWriteTest;
import com.orm.pipes.spreadSheet.SpreadSheet;

class ExcelTest extends ReadWriteTest<ExcelTestBean> {
    public ExcelTest() {
        super.init(ExcelTestBean.class, SpreadSheet.xlsx.extension);
    }
}