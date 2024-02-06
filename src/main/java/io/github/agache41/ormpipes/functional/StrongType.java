package io.github.agache41.ormpipes.functional;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <pre>
 * The StrongType encapsulates a complex parameterized Java type.
 * It consists of a main type and an optional parameter.
 * Its usage comes in place where complex type conversions have to be made.
 * After creation of two StrongType instances the method isAssignableFrom()
 * can be used between the two:
 * Example:
 *  StrongType.of("Collection&lt;String&gt;").isAssignableFrom(StrongType.of("List&lt;String&gt;"));
 *  public class A extends B {};
 *  StrongType.of("List&lt;B&gt;").isAssignableFrom(StrongType.of("List&lt;A&gt;"));
 * </pre>
 */
public class StrongType implements Type {

    private static final Map<Class<?>, Class<?>> boxingMap =
            Stream.of(new Class<?>[][]{{byte.class, Byte.class}, //
                                       {boolean.class, Boolean.class}, //
                                       {short.class, Short.class}, //
                                       {int.class, Integer.class}, //
                                       {long.class, Long.class}, //
                                       {double.class, Double.class}, //
                                       {float.class, Float.class}}) //
                  .collect(Collectors.toMap(k -> k[0], v -> v[1]));

    private static final Map<String, Class<?>> classCache =
            Stream.of(new Object[][]{{"byte", Byte.class}, //
                                     {"boolean", Boolean.class}, //
                                     {"short", Short.class}, //
                                     {"int", Integer.class}, //
                                     {"long", Long.class}, //
                                     {"double", Double.class}, //
                                     {"float", Float.class}}) //
                  .collect(Collectors.toMap(k -> (String) k[0], v -> (Class<?>) v[1], Helper.throwingMerger(" Duplicate class "), ConcurrentHashMap::new));
    private static final Map<String, StrongType> cache = new ConcurrentHashMap<>();
    private static final Pattern pattern = Pattern.compile("([^<>]+)(<(.+)>)?");
    private final String typeName;
    private final String className;
    private final Class<?> clazz;
    private final StrongType parameterType;

    private final boolean consumer;

    private StrongType(String typeName) {
        this.typeName = typeName;
        Matcher matcher = pattern.matcher(this.typeName);
        if (!matcher.matches() || matcher.groupCount() < 1) {
            throw new IllegalArgumentException("Given type : [" + this.typeName + "] could not be parsed!");
        }
        this.className = matcher.group(1);
        this.clazz = unbox(this.computeClass(this.className));
        this.consumer = ThrowingConsumer.class.equals(this.clazz) || Consumer.class.equals(this.clazz);
        String parameterTypeName = matcher.group(3);
        if (parameterTypeName != null) {
            this.parameterType = new StrongType(parameterTypeName);
        } else {
            this.parameterType = null;
        }
        this.verify(typeName);
    }

    /**
     * <pre>
     * Static singleton initialize instance method, cached in a map.
     * </pre>
     *
     * @param type the type
     * @return the strong type
     */
    public static final StrongType of(Type type) {
        return cache.computeIfAbsent(type.getTypeName(), StrongType::new);
    }

    /**
     * <pre>
     * Static singleton initialize instance method, cached in a map.
     * </pre>
     *
     * @param clazz the clazz
     * @return the strong type
     */
    public static final StrongType of(Class<?> clazz) {
        return cache.computeIfAbsent(unbox(clazz).getTypeName(), StrongType::new);
    }

    /**
     * <pre>
     * Static singleton initialize instance method, cached in a map.
     * </pre>
     *
     * @param name the name
     * @return the strong type
     */
    public static final StrongType of(String name) {
        return cache.computeIfAbsent(name, StrongType::new);
    }

    /**
     * <pre>
     * Performs unboxing of primitive types into the Objects
     * </pre>
     *
     * @param clazz the clazz
     * @return the class
     */
    public static final Class<?> unbox(Class<?> clazz) {
        if (boxingMap.containsKey(clazz)) {
            return boxingMap.get(clazz);
        } else {
            return clazz;
        }
    }

    private Class<?> computeClass(String className) {
        if (className.equals("?")) {
            return null;
        }
        if (className.endsWith("[]")) {
            className = "[L" + className.substring(0, className.length() - 2) + ";";
        }
        return classCache.computeIfAbsent(className, this::forName);
    }

    private Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void verify(String original) {
        String calculated = this.className;
        if (this.parameterType != null) {
            calculated += "<" + this.parameterType.typeName + ">";
        }
        assert original.equals(calculated);
    }


    /**
     * <pre>
     * Adds a parameter to the type.
     * </pre>
     *
     * @param subtypeName the subtype name
     * @return the strong type
     */
    public StrongType parameterizedWith(String subtypeName) {
        return StrongType.of(this.typeName + "<" + subtypeName + ">");
    }

    /**
     * <pre>
     * Adds a parameter to the type.
     * </pre>
     *
     * @param typeName the type name
     * @return the strong type
     */
    public StrongType parameterizedWith(StrongType typeName) {
        return StrongType.of(this.typeName + "<" + typeName.getTypeName() + ">");
    }

    /**
     * <pre>
     * Adds a parameter to the type.
     * </pre>
     *
     * @param type the type
     * @return the strong type
     */
    public StrongType parameterizedWith(Type type) {
        return StrongType.of(this.typeName + "<" + type.getTypeName() + ">");
    }

    /**
     * <pre>
     * Tells if this class can be assigned from the other type
     * @see Class#isAssignableFrom(Class)
     * </pre>
     *
     * @param otherType the other type
     * @return true if is it assignable, false otherwise
     */
    public boolean isAssignableFrom(StrongType otherType) {
        if (otherType == null) {
            return "?".equals(this.typeName);
        }
        if (this.typeName.equals(otherType.typeName)) {
            return true;
        }
        return this.isClassAssignableFrom(otherType.clazz) && (this.consumer ? otherType.isParameterTypeAssignableFrom(this.parameterType) :
                                                               this.isParameterTypeAssignableFrom(otherType.parameterType));
    }

    /**
     * <pre>
     * Tells if this class can be assigned from the other class
     * @see Class#isAssignableFrom(Class)
     * </pre>
     *
     * @param otherClass the other class
     * @return true if is it assignable, false otherwise
     */
    public boolean isClassAssignableFrom(Class<?> otherClass) {
        if (this.clazz == null) {
            return true;
        }
        if (otherClass == null) {
            return false;
        } else {
            return this.clazz.isAssignableFrom(otherClass);
        }
    }

    /**
     * <pre>
     * Tells if this class parameter can be assigned from the other parameter type
     * </pre>
     *
     * @param otherParameterType the other parameter type
     * @return the boolean
     */
    public boolean isParameterTypeAssignableFrom(StrongType otherParameterType) {
        if (this.parameterType == null) {
            return true;
        } else {
            return this.parameterType.isAssignableFrom(otherParameterType);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTypeName() {
        return this.typeName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.typeName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        StrongType that = (StrongType) o;
        return this.typeName.equals(that.typeName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.typeName);
    }
}
