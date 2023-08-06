package com.orm.functional;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        if (!matcher.matches() || matcher.groupCount() < 1) throw new IllegalArgumentException("Given type : [" + this.typeName + "] could not be parsed!");
        this.className = matcher.group(1);
        this.clazz = unbox(this.computeClass(this.className));
        this.consumer = ThrowingConsumer.class.equals(this.clazz) || Consumer.class.equals(this.clazz);
        String parameterTypeName = matcher.group(3);
        if (parameterTypeName != null) this.parameterType = new StrongType(parameterTypeName);
        else this.parameterType = null;
        this.verify(typeName);
    }

    public static final StrongType of(Type type) {
        return cache.computeIfAbsent(type.getTypeName(), StrongType::new);
    }

    public static final StrongType of(Class<?> clazz) {
        return cache.computeIfAbsent(unbox(clazz).getTypeName(), StrongType::new);
    }

    public static final StrongType of(String name) {
        return cache.computeIfAbsent(name, StrongType::new);
    }

    public static final Class<?> unbox(Class<?> clazz) {
        if (boxingMap.containsKey(clazz)) return boxingMap.get(clazz);
        else return clazz;
    }

    private Class<?> computeClass(String className) {
        if (className.equals("?"))
            return null;
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


    public StrongType parameterizedWith(String subtypeName) {
        return StrongType.of(this.typeName + "<" + subtypeName + ">");
    }

    public StrongType parameterizedWith(StrongType typeName) {
        return StrongType.of(this.typeName + "<" + typeName.getTypeName() + ">");
    }

    public StrongType parameterizedWith(Type type) {
        return StrongType.of(this.typeName + "<" + type.getTypeName() + ">");
    }

    public boolean isAssignableFrom(StrongType otherType) {
        if (otherType == null) return "?".equals(this.typeName);
        if (this.typeName.equals(otherType.typeName)) return true;
        return this.isClassAssignableFrom(otherType.clazz) && (this.consumer ? otherType.isParameterTypeAssignableFrom(this.parameterType) :
                this.isParameterTypeAssignableFrom(otherType.parameterType));
    }

    public boolean isClassAssignableFrom(Class<?> otherClass) {
        if (this.clazz == null) return true;
        if (otherClass == null) return false;
        else return this.clazz.isAssignableFrom(otherClass);
    }

    public boolean isParameterTypeAssignableFrom(StrongType otherParameterType) {
        if (this.parameterType == null) return true;
        else
            return this.parameterType.isAssignableFrom(otherParameterType);
    }

    @Override
    public String getTypeName() {
        return this.typeName;
    }

    @Override
    public String toString() {
        return this.typeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        StrongType that = (StrongType) o;
        return this.typeName.equals(that.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.typeName);
    }
}
