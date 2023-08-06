package com.orm.pipes.baseTest.values;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public abstract class AbstractValuesFeeder<T extends TestBean> implements Values<T>, Supplier<T> {
    @Override
    public abstract T get();

    @Override
    public List<T> getValues() {
        final T nuller = this.get();
        final T minValue = this.get();
        minValue.setString0("");
        minValue.setInteger1(Integer.MIN_VALUE);
        minValue.setLong2(Long.MIN_VALUE);
        minValue.setDouble3(Double.MIN_VALUE);
        minValue.setFloat4(Float.MIN_VALUE);
        minValue.setLocaldate5(LocalDate.of(1900, 1, 1));
        minValue.setDate6(Date.valueOf(LocalDate.of(1900, 1, 1)));
        minValue.setList7(Arrays.asList("1", "2", "3"));

        final T value = this.get();
        value.setString0("string0");
        value.setInteger1(1);
        value.setLong2(2L);
        value.setDouble3(3.0);
        value.setFloat4(4.0F);
        value.setLocaldate5(LocalDate.of(2022, 2, 22));
        value.setDate6(Date.valueOf(LocalDate.of(2022, 2, 22)));
        value.setList7(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j"));

        return new ArrayList<>(Arrays.asList(nuller, minValue, value));
    }

    @Override
    public List<T> getLessValues() {
        final T nuller = this.get();
        final T lessMinValue = this.get();
        lessMinValue.setString0("");
        lessMinValue.setInteger1(Integer.MIN_VALUE);
        lessMinValue.setDouble3(Double.MIN_VALUE);
        lessMinValue.setLocaldate5(LocalDate.of(1900, 1, 1));

        final T lessValue = this.get();
        lessValue.setString0("string0");
        lessValue.setInteger1(1);
        lessValue.setDouble3(3.0);
        lessValue.setLocaldate5(LocalDate.of(2022, 2, 22));

        return new ArrayList<>(Arrays.asList(nuller, lessMinValue, lessValue));
    }

    protected void assertEquals(Collection<T> expected, Collection<T> actual) {
        if (!CollectionUtils.isEqualCollection(expected, actual)) {
            System.out.println("size=" + expected.size() + ":" + expected);
            System.out.println("size=" + actual.size() + ":" + actual);
            Assertions.fail("Collections not equal!");
        }
    }

    protected void assertNotEquals(Collection<T> expected, Collection<T> actual) {
        if (CollectionUtils.isEqualCollection(expected, actual)) {
            System.out.println("size=" + expected.size() + ":" + expected);
            System.out.println("size=" + actual.size() + ":" + actual);
            Assertions.fail("Collections are actually equal!");
        }
    }
}
