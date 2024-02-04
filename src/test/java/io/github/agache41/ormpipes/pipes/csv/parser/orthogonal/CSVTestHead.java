package io.github.agache41.ormpipes.pipes.csv.parser.orthogonal;

import io.github.agache41.ormpipes.pipes.baseTest.ExtendedReadWriteTest;
import io.github.agache41.ormpipes.pipes.baseTest.values.TestBean;
import io.github.agache41.ormpipes.pipes.csv.csvFile.CSVFile;

public abstract class CSVTestHead<T extends TestBean> extends ExtendedReadWriteTest<T> {
    protected void init(Class<T> clazz) {
        super.init(clazz, CSVFile.extension);
    }
}
