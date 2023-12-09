package com.orm.pipes.baseTest;

import com.orm.pipes.baseTest.values.TestBean;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;


public abstract class ReadWriteTest<T extends TestBean> extends AbstractBaseStreamTest<T>{
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
        Assertions.assertTrue(this.file.exists());
        Assertions.assertTrue(CollectionUtils.isEqualCollection(this.values, this.readFile("")));
    }
}