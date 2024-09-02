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

package io.github.agache41.ormpipes.pipe.registry;

import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.annotator.annotator.Annotator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ToStringAnnotTest
@Execution(ExecutionMode.CONCURRENT)
public class RegistryTest {

    @Test
    public void createAndConfigureReadOK() {
        ToStringAnnotTest annotation = Annotator.of(RegistryTest.class)
                                                .getAnnotation(ToStringAnnotTest.class,
                                                        true);
        try {
            AnnotablePipe result = Registry.createAndConfigureFromMethod(AnnotablePipe.class,
                    annotation,
                    "read",
                    RegistryTest.class);
            assertNotNull(result);
            assertEquals("10",
                    result.function()
                          .apply(10));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createAndConfigureNOK1() {
        ToStringAnnotTest annotation = Annotator.of(RegistryTest.class)
                                                .getAnnotation(ToStringAnnotTest.class,
                                                        true);
        try {
            AnnotablePipe result = Registry.createAndConfigureFromMethod(AnnotablePipe.class,
                    annotation,
                    "list",
                    RegistryTest.class);
            Assertions.fail("Exception was not thrown!");
        } catch (Exception e) {
            assertNotNull(e);
            assertEquals(ClassCastException.class,
                    e.getClass());
            assertEquals("Method list(...) in ToStringAnnotTest does not return a AnnotablePipe!",
                    e.getMessage());
        }
    }

    @Test
    public void createAndConfigureNOK2() {
        ToStringAnnotTest annotation = Annotator.of(RegistryTest.class)
                                                .getAnnotation(ToStringAnnotTest.class,
                                                        true);
        try {
            AnnotablePipe result = Registry.createAndConfigureFromMethod(AnnotablePipe.class,
                    annotation,
                    "blabla",
                    RegistryTest.class);
            Assertions.fail("Exception was not thrown!");
        } catch (Exception e) {
            assertNotNull(e);
            assertEquals(IllegalArgumentException.class,
                    e.getClass());
            assertEquals("No such method or field blabla in ToStringAnnotTest!",
                    e.getMessage());
        }
    }

    @Test
    public void createAndConfigureNOK3() {
        ToStringAnnotTest annotation = Annotator.of(RegistryTest.class)
                                                .getAnnotation(ToStringAnnotTest.class,
                                                        true);
        try {
            AnnotablePipe result = Registry.createAndConfigureFromMethod(AnnotablePipe.class,
                    annotation,
                    "enabledOn",
                    RegistryTest.class);
            Assertions.fail("Exception was not thrown!");
        } catch (Exception e) {
            assertNotNull(e);
            assertEquals(ClassCastException.class,
                    e.getClass());
            assertEquals("ToStringAnnotTest.acc.enabledOn returns a String[] which can not be assigned to a Class!",
                    e.getMessage());
        }
    }
}