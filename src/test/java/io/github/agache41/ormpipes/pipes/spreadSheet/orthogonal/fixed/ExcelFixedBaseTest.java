package io.github.agache41.ormpipes.pipes.spreadSheet.orthogonal.fixed;

import io.github.agache41.ormpipes.pipes.baseTest.values.TestBean;
import io.github.agache41.ormpipes.pipes.spreadSheet.orthogonal.ExtendedExcelTest;
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
