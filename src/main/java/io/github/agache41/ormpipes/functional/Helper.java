package io.github.agache41.ormpipes.functional;

import java.lang.reflect.Constructor;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

/**
 * <pre>
 * The type Helper.
 * </pre>
 */
public class Helper {
    /**
     * <pre>
     * Throwing merger binary operator.
     * </pre>
     *
     * @param <T>     the type parameter
     * @param message the message
     * @return the binary operator
     */
    public static <T> BinaryOperator<T> throwingMerger(String message) {
        return (u, v) -> {
            throw new IllegalStateException(String.format(message + "%s", u));
        };
    }

    /**
     * <pre>
     * Constructor supplier.
     * </pre>
     *
     * @param <T>     the type parameter
     * @param ofClass the of class
     * @return the supplier
     */
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
