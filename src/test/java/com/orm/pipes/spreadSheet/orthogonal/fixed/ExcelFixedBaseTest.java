package com.orm.pipes.spreadSheet.orthogonal.fixed;

import com.orm.pipes.baseTest.values.TestBean;
import com.orm.pipes.spreadSheet.orthogonal.ExtendedExcelTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public abstract class ExcelFixedBaseTest<T extends TestBean> extends ExtendedExcelTest<T> {

    @Test
    @Order(2)
    void testReadMoreColumns() {
        Assertions.assertThrows(RuntimeException.class, () -> this.readFile("-moreColumns"));
    }

    @Test
    @Order(3)
    void testReadLessColumns() {
        Assertions.assertThrows(RuntimeException.class, () -> this.readFile("-lessColumns"));
    }
}
