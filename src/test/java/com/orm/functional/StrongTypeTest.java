package com.orm.functional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

class StrongTypeTest {

    public static final String JAVA_LANG_STRING = String.class.getCanonicalName();
    public static final String JAVA_UTIL_STREAM_STREAM = Stream.class.getCanonicalName();
    public static final String THROWING_CONSUMER = ThrowingConsumer.class.getCanonicalName();

    @Test
    void testFail() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> StrongType.of(""));
        Assertions.assertThrows(IllegalArgumentException.class, () -> StrongType.of("bla bla"));
    }

    @Test
    void testSuccess() {
        Assertions.assertNotNull(StrongType.of(JAVA_LANG_STRING));
        Assertions.assertNotNull(StrongType.of(JAVA_UTIL_STREAM_STREAM + "<" + JAVA_LANG_STRING + ">"));
        Assertions.assertNotNull(StrongType.of(THROWING_CONSUMER + "<" + JAVA_UTIL_STREAM_STREAM + "<" + JAVA_LANG_STRING + ">>"));
    }

    @Test
    void testIsAssignableFrom() {
        Assertions.assertTrue(StrongType.of(JAVA_LANG_STRING)
                                        .isAssignableFrom(StrongType.of(JAVA_LANG_STRING)));
        Assertions.assertFalse(StrongType.of(JAVA_LANG_STRING)
                                         .isAssignableFrom(StrongType.of("?")));
        Assertions.assertTrue(StrongType.of("?")
                                        .isAssignableFrom(StrongType.of(JAVA_LANG_STRING)));

        Assertions.assertTrue(StrongType.of(JAVA_UTIL_STREAM_STREAM + "<" + JAVA_LANG_STRING + ">")
                                        .isAssignableFrom(StrongType.of(JAVA_UTIL_STREAM_STREAM + "<" + JAVA_LANG_STRING + ">")));
        Assertions.assertTrue(StrongType.of(JAVA_UTIL_STREAM_STREAM + "<?>")
                                        .isAssignableFrom(StrongType.of(JAVA_UTIL_STREAM_STREAM + "<?>")));
        Assertions.assertFalse(StrongType.of(JAVA_UTIL_STREAM_STREAM + "<" + JAVA_LANG_STRING + ">")
                                         .isAssignableFrom(StrongType.of(JAVA_UTIL_STREAM_STREAM + "<?>")));
        Assertions.assertTrue(StrongType.of(JAVA_UTIL_STREAM_STREAM + "<?>")
                                        .isAssignableFrom(StrongType.of(JAVA_UTIL_STREAM_STREAM + "<" + JAVA_LANG_STRING + ">")));

    }

    @Test
    void testIsAssignableReverseFrom() {
        Assertions.assertTrue(StrongType.of(THROWING_CONSUMER + "<" + JAVA_LANG_STRING + ">")
                                        .isAssignableFrom(StrongType.of(THROWING_CONSUMER + "<?>")));
        Assertions.assertFalse(StrongType.of(THROWING_CONSUMER + "<?>")
                                         .isAssignableFrom(StrongType.of(THROWING_CONSUMER + "<" + JAVA_LANG_STRING + ">")));
    }


    @Test
    void testGenericIsAssignableFrom() {
        Assertions.assertTrue(StrongType.of(List.class)
                                        .isAssignableFrom(StrongType.of(List.class)
                                                                    .parameterizedWith(String.class)));

        Assertions.assertFalse(StrongType.of(List.class)
                                         .parameterizedWith(String.class)
                                         .isAssignableFrom(StrongType.of(List.class)));
        Assertions.assertTrue(StrongType.of(List.class)
                                        .parameterizedWith("?")
                                        .isAssignableFrom(StrongType.of(List.class)));

        Assertions.assertFalse(StrongType.of(List.class)
                                         .parameterizedWith(String.class)
                                         .isAssignableFrom(StrongType.of(List.class)
                                                                     .parameterizedWith("?")));

        Assertions.assertTrue(StrongType.of(List.class)
                                        .parameterizedWith("?")
                                        .isAssignableFrom(StrongType.of(List.class)
                                                                    .parameterizedWith(String.class)));
    }

    @Test
    void testPrimitivesIsAssignableFrom() {
        Assertions.assertTrue(StrongType.of("int")
                                        .isAssignableFrom(StrongType.of(Integer.class)));
        Assertions.assertTrue(StrongType.of(Integer.class)
                                        .isAssignableFrom(StrongType.of("int")));
        Assertions.assertTrue(StrongType.of("long")
                                        .isAssignableFrom(StrongType.of(Long.class)));
        Assertions.assertTrue(StrongType.of(Long.class)
                                        .isAssignableFrom(StrongType.of("long")));
        Assertions.assertFalse(StrongType.of("int")
                                         .isAssignableFrom(StrongType.of(Long.class)));
        Assertions.assertFalse(StrongType.of("long")
                                         .isAssignableFrom(StrongType.of(Integer.class)));
    }

}