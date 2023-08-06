package com.orm.pipes.spreadSheet;

import com.orm.pipes.baseTest.TestHead;
import com.orm.pipes.baseTest.values.TestBean;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.Collections;


public abstract class ExcelBaseTest<T extends TestBean> extends TestHead<T> {


    @Test
    @Order(0)
    @Execution(ExecutionMode.SAME_THREAD)
    void testWrite() throws Throwable {
        this.file.delete();
        Assertions.assertFalse(this.file.exists());
        this.parser.write(this.testFileName, this.values.stream());
        Assertions.assertTrue(this.file.exists());
        Assertions.assertTrue(this.file.delete());
        Assertions.assertFalse(this.file.exists());
        this.parser.write(this.testFileName, this.values.stream());
        Assertions.assertTrue(this.file.exists());
    }

    @Test
    @Order(10)
    @Execution(ExecutionMode.SAME_THREAD)
    void testRead() throws Throwable {
        Assertions.assertTrue(CollectionUtils.isEqualCollection(this.values, this.readFile("")));
        Assertions.assertTrue(this.file.delete());
        Assertions.assertFalse(this.file.exists());
    }

    @Disabled
    @Test
    @Order(20)
    @Execution(ExecutionMode.SAME_THREAD)
    void testReadMoreColumns() throws Throwable {
        Assertions.assertTrue(CollectionUtils.isEqualCollection(this.values, this.readFile("-moreColumns")));
    }

    @Disabled
    @Test
    @Order(30)
    void testReadLessColumns() throws Throwable {
        Assertions.assertTrue(CollectionUtils.isEqualCollection(this.lessValues, this.readFile("-lessColumns")));
    }

    @Disabled
    @Test
    @Order(40)
    void testReadLessRequiredColumns() throws Throwable {
        Assertions.assertThrows(RuntimeException.class, () -> this.readFile("-lessRequiredColumns"));
    }

    @Disabled
    @Test
    @Order(50)
    void testEmptyFile() throws Throwable {
        Assertions.assertThrows(RuntimeException.class, () -> this.readFile("-empty"));
    }

    @Disabled
    @Test
    @Order(60)
    void testEmptyDataFile() throws Throwable {
        Assertions.assertTrue(CollectionUtils.isEqualCollection(Collections.emptyList(), this.readFile("-emptyData")));
    }

    @Disabled
    @Test
    @Order(70)
    void testEmptyLinesFile() throws Throwable {
        Assertions.assertThrows(RuntimeException.class, () -> this.readFile("-emptyLines"));
    }
}