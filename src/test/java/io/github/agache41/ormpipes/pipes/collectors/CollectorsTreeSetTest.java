package io.github.agache41.ormpipes.pipes.collectors;

import io.github.agache41.ormpipes.pipes.baseTest.AbstractBaseGenericTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.TreeSet;

public class CollectorsTreeSetTest extends AbstractBaseGenericTest<CollectorsTestBean, Set<CollectorsTestBean>> {

    public CollectorsTreeSetTest() {
        super.init(CollectorsTestBean.class, "treeSet", ".csv");
    }

    @Test
    void test() throws Throwable {
        Set<CollectorsTestBean> result = this.writeReadFile("", new TreeSet<>(this.values));
        Assertions.assertTrue(result instanceof TreeSet);
    }
}
