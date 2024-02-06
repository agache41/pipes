package io.github.agache41.ormpipes.pipes.spreadSheet.orthogonal.variablePositions;

import io.github.agache41.ormpipes.pipes.baseTest.values.TestBean;
import io.github.agache41.ormpipes.pipes.spreadSheet.orthogonal.ExtendedExcelTest;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

public abstract class ExcelVariableNamesBaseTest<T extends TestBean> extends ExtendedExcelTest<T> {

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
