package com.orm.pipe.registry;

import com.orm.pipe.AnnotablePipe;
import com.orm.reflection.annotator.Annotator;
import com.orm.reflection.registry.Registry;
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