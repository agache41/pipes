package com.orm.pipes.spreadSheet.xsl;

import com.orm.pipes.baseTest.TestHead;
import com.orm.pipes.baseTest.values.TestBean;
import com.orm.pipes.spreadSheet.SpreadSheet;

public class ExcelTestHead<T extends TestBean> extends TestHead<T> {
    protected void init(Class<T> clazz) {
        super.init(clazz, SpreadSheet.xls.extension);
    }
}
