package io.github.agache41.ormpipes.pipes.csv.parser.orthogonal.fixed;

import io.github.agache41.ormpipes.pipes.baseTest.values.TestBean;
import io.github.agache41.ormpipes.pipes.csv.parser.orthogonal.CSVTestHead;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public abstract class CSVFixedBaseTest<T extends TestBean> extends CSVTestHead<T> {

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
