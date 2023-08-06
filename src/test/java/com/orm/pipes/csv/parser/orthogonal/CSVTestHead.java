package com.orm.pipes.csv.parser.orthogonal;

import com.orm.pipes.baseTest.TestHead;
import com.orm.pipes.baseTest.values.TestBean;
import com.orm.pipes.csv.csvFile.CSVFile;

public abstract class CSVTestHead<T extends TestBean> extends TestHead<T> {
    protected void init(Class<T> clazz) {
        super.init(clazz, CSVFile.extension);
    }
}
