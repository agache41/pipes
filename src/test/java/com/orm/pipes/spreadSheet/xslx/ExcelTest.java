package com.orm.pipes.spreadSheet.xslx;

import com.orm.pipes.spreadSheet.ExcelBaseTest;
import com.orm.pipes.spreadSheet.SpreadSheet;

class ExcelTest extends ExcelBaseTest<ExcelTestBean> {
    public ExcelTest() {
        super.init(ExcelTestBean.class, SpreadSheet.xlsx.extension);
    }
}