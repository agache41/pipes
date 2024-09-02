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

package io.github.agache41.ormpipes.pipes.spreadSheet.orthogonal;

import io.github.agache41.ormpipes.pipes.baseTest.ExtendedReadWriteTest;
import io.github.agache41.ormpipes.pipes.baseTest.values.TestBean;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.Collections;

public abstract class ExtendedExcelTest<T extends TestBean> extends ExtendedReadWriteTest<T> {
    protected void init(Class<T> clazz) {
        super.init(clazz, SpreadSheet.xls.extension);
    }

    @Test
    @Order(6)
    @Execution(ExecutionMode.SAME_THREAD)
    void testEmptyLinesFile() throws Throwable {
        this.assertEquals(Collections.emptyList(), this.readFile("-emptyLines"));
    }
}
