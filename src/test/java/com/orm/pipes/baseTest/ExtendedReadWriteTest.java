package com.orm.pipes.baseTest;

import com.orm.pipes.baseTest.values.TestBean;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.Collections;


public abstract class ExtendedReadWriteTest<T extends TestBean> extends ReadWriteTest<T> {
    @Test
    @Order(2)
    @Execution(ExecutionMode.SAME_THREAD)
    void testReadMoreColumns() throws Throwable {
        this.assertEquals(this.values, this.readFile("-moreColumns"));
    }

    @Test
    @Order(3)
    @Execution(ExecutionMode.SAME_THREAD)
    void testReadLessColumns() throws Throwable {
        Assertions.assertTrue(CollectionUtils.isEqualCollection(this.lessValues, this.readFile("-lessColumns")));
    }

    @Test
    @Order(4)
    @Execution(ExecutionMode.SAME_THREAD)
    void testReadLessRequiredColumns() {
        Assertions.assertThrows(RuntimeException.class, () -> this.readFile("-lessRequiredColumns"));
    }

    @Test
    @Order(5)
    @Execution(ExecutionMode.SAME_THREAD)
    void testEmptyFile() {
        Assertions.assertThrows(RuntimeException.class, () -> this.readFile("-empty"));
    }

    @Test
    @Order(5)
    @Execution(ExecutionMode.SAME_THREAD)
    void testEmptyDataFile() throws Throwable {
        this.assertEquals(Collections.emptyList(), this.readFile("-emptyData"));
    }

    @Test
    @Order(6)
    @Execution(ExecutionMode.SAME_THREAD)
    void testEmptyLinesFile() {
        Assertions.assertThrows(RuntimeException.class, () -> this.readFile("-emptyLines"));
    }
}
