package com.orm.pipes.spreadSheet.orthogonal.fixed;

import com.orm.pipes.baseTest.values.TestBean;
import com.orm.pipes.spreadSheet.orthogonal.ExcelTestHead;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public abstract class ExcelFixedBaseTest<T extends TestBean> extends ExcelTestHead<T> {

    @Test
    @Order(2)
    void testReadMoreColumns() throws Throwable {
        Assertions.assertThrows(RuntimeException.class, () -> this.readFile("-moreColumns"));
    }

    @Test
    @Order(3)
    void testReadLessColumns() throws Throwable {
        Assertions.assertThrows(RuntimeException.class, () -> this.readFile("-lessColumns"));
    }
}
