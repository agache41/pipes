/*
 *    Copyright 2022-2023  Alexandru Agache
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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
