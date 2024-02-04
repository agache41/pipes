package io.github.agache41.ormpipes.pipes.collectors;

import io.github.agache41.ormpipes.pipes.baseTest.AbstractBaseGenericTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CollectorsListTest extends AbstractBaseGenericTest<CollectorsTestBean, List<CollectorsTestBean>> {

    public CollectorsListTest() {
        super.init(CollectorsTestBean.class, "list", ".csv");
    }

    @Test
    void test() throws Throwable {
        List<CollectorsTestBean> result = this.writeReadFile("", this.values);
        Assertions.assertTrue(result instanceof List);
    }
}
