package io.github.agache41.ormpipes.pipes.csv.parser.orthogonal.variableName;

import io.github.agache41.ormpipes.pipes.baseTest.values.TestBean;
import io.github.agache41.ormpipes.pipes.csv.parser.orthogonal.CSVTestHead;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public abstract class CSVVariableNameBaseTest<T extends TestBean> extends CSVTestHead<T> {

    @Test
    @Order(2)
    void testReadMoreColumns() throws Throwable {
        Assertions.assertFalse(CollectionUtils.isEqualCollection(this.values, this.readFile("-moreColumns")));
    }

    @Test
    @Order(3)
    void testReadLessColumns() throws Throwable {
        Assertions.assertFalse(CollectionUtils.isEqualCollection(this.lessValues, this.readFile("-lessColumns")));
    }
}
