package com.orm.pipes.spreadSheet.orthogonal.variablePositions;

import com.orm.pipes.baseTest.values.TestBean;
import com.orm.pipes.spreadSheet.orthogonal.ExcelTestHead;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

public abstract class ExcelVariableNamesBaseTest<T extends TestBean> extends ExcelTestHead<T> {

    @Test
    @Order(3)
    void testReadLessColumns() throws Throwable {
        Assertions.assertFalse(CollectionUtils.isEqualCollection(this.lessValues, this.readFile("-lessColumns")));
    }

    @Test
    @Order(2)
    @Execution(ExecutionMode.SAME_THREAD)
    void testReadMoreColumns() throws Throwable {
        this.assertNotEquals(this.values, this.readFile("-moreColumns"));
    }
}
