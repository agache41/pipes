package com.orm.pipes.baseTest;

import com.orm.pipes.baseTest.values.TestBean;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.Collections;
import java.util.List;


public abstract class TestHead<T extends TestBean> extends AbstractBaseStreamTest<T> {
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
    @Order(1)
    @Execution(ExecutionMode.SAME_THREAD)
    void testRead() throws Throwable {
        List<T> readValues = this.readFile("");
        System.out.println("Comparing expected = read");
        System.out.println(this.values);
        System.out.println(readValues);
        Assertions.assertTrue(CollectionUtils.isEqualCollection(this.values, readValues));
        Assertions.assertTrue(this.file.delete());
        Assertions.assertFalse(this.file.exists());
    }

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
    void testReadLessRequiredColumns() throws Throwable {
        Assertions.assertThrows(RuntimeException.class, () -> this.readFile("-lessRequiredColumns"));
    }

    @Test
    @Order(5)
    @Execution(ExecutionMode.SAME_THREAD)
    void testEmptyFile() throws Throwable {
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
    void testEmptyLinesFile() throws Throwable {
        Assertions.assertThrows(RuntimeException.class, () -> this.readFile("-emptyLines"));
    }
}
