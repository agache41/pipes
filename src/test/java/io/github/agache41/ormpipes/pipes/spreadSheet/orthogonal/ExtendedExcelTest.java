package io.github.agache41.ormpipes.pipes.spreadSheet.orthogonal;

import io.github.agache41.ormpipes.pipes.baseTest.ExtendedReadWriteTest;
import io.github.agache41.ormpipes.pipes.baseTest.values.TestBean;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.Collections;

public abstract class ExtendedExcelTest<T extends TestBean> extends ExtendedReadWriteTest<T> {
    protected void init(Class<T> clazz) {
        super.init(clazz, SpreadSheet.xls.extension);
    }

    @Test
    @Order(6)
    @Execution(ExecutionMode.SAME_THREAD)
    void testEmptyLinesFile() throws Throwable {
        this.assertEquals(Collections.emptyList(), this.readFile("-emptyLines"));
    }
}
