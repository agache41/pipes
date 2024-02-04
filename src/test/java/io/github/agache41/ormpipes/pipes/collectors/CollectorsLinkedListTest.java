package io.github.agache41.ormpipes.pipes.collectors;

import io.github.agache41.ormpipes.pipes.baseTest.AbstractBaseGenericTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

public class CollectorsLinkedListTest extends AbstractBaseGenericTest<CollectorsTestBean, LinkedList<CollectorsTestBean>> {

    public CollectorsLinkedListTest() {
        super.init(CollectorsTestBean.class, "linkedList", ".csv");
    }

    @Test
    void test() throws Throwable {
        LinkedList<CollectorsTestBean> result = this.writeReadFile("", new LinkedList<>(this.values));
        Assertions.assertTrue(result instanceof LinkedList);
    }
}
