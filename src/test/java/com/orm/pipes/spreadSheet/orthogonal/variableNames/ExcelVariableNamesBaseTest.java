package com.orm.pipes.spreadSheet.orthogonal.variableNames;

import com.orm.pipes.baseTest.values.TestBean;
import com.orm.pipes.spreadSheet.orthogonal.ExtendedExcelTest;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.Collections;

public abstract class ExcelVariableNamesBaseTest<T extends TestBean> extends ExtendedExcelTest<T> {

    @Test
    @Order(2)
    void testReadMoreColumns() {
        Assertions.assertThrows(RuntimeException.class, () -> this.readFile("-moreColumns"));
    }

    @Test
    @Order(3)
    void testReadLessColumns() throws Throwable {
        Assertions.assertFalse(CollectionUtils.isEqualCollection(this.lessValues, this.readFile("-lessColumns")));
    }

    @Test
    @Order(4)
    @Execution(ExecutionMode.SAME_THREAD)
    void testReadLessRequiredColumns() throws Throwable {
        Assertions.assertFalse(CollectionUtils.isEqualCollection(this.lessValues, this.readFile("-lessRequiredColumns")));
    }

    @Test
    @Order(5)
    @Execution(ExecutionMode.SAME_THREAD)
    void testEmptyFile() throws Throwable {
        this.assertEquals(Collections.emptyList(), this.readFile("-empty"));
    }
}
