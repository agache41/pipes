package com.orm.functional;

import java.lang.reflect.Constructor;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

public class Helper {
    public static <T> BinaryOperator<T> throwingMerger(String message) {
        return (u, v) -> {
            throw new IllegalStateException(String.format(message + "%s", u));
        };
    }

    public static <T> Supplier<T> constructor(Class<T> ofClass) {
        try {
            final Constructor<T> constructor = ofClass.getConstructor();
            return () -> {
                try {
                    return constructor.newInstance();
                } catch (ReflectiveOperationException e) {
                    throw new RuntimeException(e);
                }
            };
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(" Class " + ofClass.getSimpleName() + " does not have an empty constructor!", e);
        }
    }
}
