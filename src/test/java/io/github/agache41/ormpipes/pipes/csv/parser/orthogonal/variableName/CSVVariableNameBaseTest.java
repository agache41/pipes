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
