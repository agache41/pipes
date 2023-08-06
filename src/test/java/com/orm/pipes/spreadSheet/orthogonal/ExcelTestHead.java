package com.orm.pipes.spreadSheet.orthogonal;

import com.orm.pipes.baseTest.TestHead;
import com.orm.pipes.baseTest.values.TestBean;
import com.orm.pipes.spreadSheet.SpreadSheet;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.Collections;

public class ExcelTestHead<T extends TestBean> extends TestHead<T> {
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
