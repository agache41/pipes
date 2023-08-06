package com.orm.pipes.collectors;

import com.orm.pipes.baseTest.AbstractBaseGenericTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class CollectorsSetTest extends AbstractBaseGenericTest<CollectorsTestBean, Set<CollectorsTestBean>> {

    public CollectorsSetTest() {
        super.init(CollectorsTestBean.class, "set", ".csv");
    }

    @Test
    void test() throws Throwable {
        Set<CollectorsTestBean> result = this.writeReadFile("", new HashSet<>(this.values));
        Assertions.assertTrue(result instanceof Set);
    }
}
