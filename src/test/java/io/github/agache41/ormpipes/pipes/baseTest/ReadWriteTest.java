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

package io.github.agache41.ormpipes.pipes.baseTest;

import io.github.agache41.ormpipes.pipes.baseTest.values.TestBean;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;


public abstract class ReadWriteTest<T extends TestBean> extends AbstractBaseStreamTest<T>{
    @Test
    @Order(0)
    @Execution(ExecutionMode.SAME_THREAD)
    void testWrite() throws Throwable {
        this.file.delete();
        Assertions.assertFalse(this.file.exists());
        this.parser.write(this.testFileName, this.values.stream());
        Assertions.assertTrue(this.file.exists());
        Assertions.assertTrue(this.file.delete());
        Assertions.assertFalse(this.file.exists());
        this.parser.write(this.testFileName, this.values.stream());
        Assertions.assertTrue(this.file.exists());
    }

    @Test
    @Order(1)
    @Execution(ExecutionMode.SAME_THREAD)
    void testRead() throws Throwable {
        Assertions.assertTrue(this.file.exists());
        Assertions.assertTrue(CollectionUtils.isEqualCollection(this.values, this.readFile("")));
    }
}